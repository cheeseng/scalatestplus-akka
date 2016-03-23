/*
 * Copyright 2016 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scalatestplus.akka

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{LinkedBlockingDeque, TimeUnit}
import javax.xml.ws.spi.Invoker

import akka.actor.{RepointableRef, ExtendedActorSystem, ActorRef}
import akka.testkit.TestActor.Message
import akka.testkit.{CallingThreadDispatcher, TestActor, TestKit, TestKitBase}
import org.scalatest.PrivateMethodTester.PrivateMethod

import scala.concurrent.{duration, Future}

import org.scalatest._
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

/**
 * Create async versions of expectMsg, which has this signature and description:
 *
 * def expectMsg[T](d: Duration, msg: T): T
 *
 * The given message object must be received within the specified time; the object will be returned.
 */
trait ReceivingMsg extends PatienceConfiguration with PrivateMethodTester {
  this :TestKitBase with AsyncTestSuite =>

  import org.scalatest.Assertions._

  import duration._

  // TODO: We can use different queue here
  private val queue = new LinkedBlockingDeque[Message]()
  // Probably should move this to a companion object like TestKit
  private[scalatestplus] val testActorId = new AtomicInteger(0)

  override abstract val testActor: ActorRef = {
    val impl = system.asInstanceOf[ExtendedActorSystem]
    val ref = impl.systemActorOf(TestActor.props(queue)
      .withDispatcher(CallingThreadDispatcher.Id),
      "%s-%d".format(testActorName, testActorId.incrementAndGet))
    awaitCond(ref match {
      case r if r.getClass.getName == "akka.actor.RepointableRef" ⇒ (new Invoker(r)).invokePrivate[Boolean](new Invocation('isStarted))//r.isStarted
      case _                 ⇒ true
    }, 1 second, 10 millis)
    ref
  }

  def receivingMsg[T](msg: T)(implicit config: PatienceConfig): Future[T] = {
    receivingMsg(msg, config.timeout)
  }

  def receivingMsg[T](msg: T, timeout: Span): Future[T] = Future {
    val duration = FiniteDuration(timeout.toNanos, TimeUnit.NANOSECONDS)
    expectMsg(duration, msg)
  }

  def assertingReceiveMsg[T](msg: T)(implicit config: PatienceConfig): Future[Assertion] = {
    assertingReceiveMsg(msg, config.timeout)
  }

  def assertingReceiveMsg[T](msg: T, timeout: Span): Future[Assertion] = {
    receivingMsg(msg, timeout).map(_ => Succeeded)
  }

}

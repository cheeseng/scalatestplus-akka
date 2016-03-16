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

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import org.scalatest.{AsyncWordSpecLike, BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.time.{Seconds, Span}
import java.util.concurrent.ExecutionException

class AsyncReceivingAnyClassOfSpec(system: ActorSystem) extends TestKit(system) with AsyncTestKitLike with ImplicitSender
  with AsyncWordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("ExampleSpec"))

  override def afterAll() = {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo actor" should {
    "async send back messages of same type" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      val fut = receivingAnyClassOf(classOf[String])
      fut.map(_ => succeed)
    }
    "receivingAnyClassOf should match from list of classes" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      val fut = receivingAnyClassOf(classOf[Int], classOf[Double], classOf[String], classOf[Any])
      fut.map(_ => succeed)
    }
    "receivingAnyClassOf should not match for number classes" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      val futureEx = recoverToExceptionIf[ExecutionException] {
        receivingAnyClassOf(classOf[Int], classOf[Double])
      }
      futureEx.map(ex => Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }
}


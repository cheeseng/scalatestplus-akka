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

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Matchers
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.AsyncWordSpecLike
import org.scalatest.exceptions.TestFailedException
import org.scalatest.time.{Milliseconds, Millisecond, Span}

import scala.concurrent.Future

class AsyncExampleSpec(system: ActorSystem) extends TestKit(system) with AsyncTestKitLike with ImplicitSender
  with AsyncWordSpecLike with Matchers with BeforeAndAfterAll with ReceivingNoMsg {

  def this() = this(ActorSystem("ExampleSpec"))

  override def afterAll() = {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo actor" should {
    "send back messages unchanged" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      val fut = Future { expectMsg("hello world") }
      fut.map(_ => succeed)
    }

    "not send any messages on its own" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "ping"
      expectMsg("ping")
      assertingReceiveNoMsg(Span(1000, Milliseconds))
    }

    "send back messages" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "ping"
      recoverToSucceededIf[TestFailedException](assertingReceiveNoMsg(Span(1000, Milliseconds)))
    }
  }
}

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
import akka.testkit.{ImplicitSender, TestActors, TestKit}
import org.scalatest.{AsyncWordSpecLike, BeforeAndAfterAll, Matchers}
import org.scalatest.exceptions.TestFailedException
import org.scalatest.time.{Milliseconds, Span}

import scala.concurrent.Future

class AsyncExampleSpec(system: ActorSystem) extends TestKit(system) with AsyncTestKitLike with ImplicitSender
  with AsyncWordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("ExampleSpec"))

  override def afterAll() = {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo actor" should {
    "send back messages unchanged" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      val fut = Future {
        expectMsg("hello world")
      }
      fut.map(_ => succeed)
    }
  }
  "An async test" can {
    "be written in terms of a for expression" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"

      // receivingA[T] returns a Future[T] if received by
      // the test actor before the patience expires. So it
      // is similar to Akka TestKit's expectMsgType[T], but
      // for async. The implementation currently blocks, but
      // hopefully could be made non-blocking.
      //
      // This for demonstrates a multi-step scenario being
      // tested all while remaining in future space.
      for {
        str <- receivingA[String]
        num <- {
          echo ! 33
          receivingAn[Int]
        }
        _ = { // can do mid-stream assertions
          num should equal (33)
          str should startWith ("hello")
        }
        (numStr, strLen) <- {
          echo ! (num.toString, str.length)
          receivingA[(String, Int)]
        }

      } yield { // must do at least one assertion at the end
        numStr should be ("33")
        strLen should be (11)
      }
    }
  }
}

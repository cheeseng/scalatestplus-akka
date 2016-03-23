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
import akka.testkit.TestActors
import org.scalatest.{AsyncWordSpecLike, BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.time.{Seconds, Span}
import java.util.concurrent.ExecutionException

class ReceivingAnyOfSpec(system: ActorSystem) extends AsyncSpecBase(system) {

  def this() = this(ActorSystem("ReceivingAnyOfSpec"))

  "receivingAnyOf" should {
    "async send back messages of same type" in {
      echo ! "hello world"
      val fut = receivingAnyOf("hello world")
      fut.map(_ => succeed)
    }
    "async send back messages of same type with span" in {
      echo ! "hello world"
      val fut = receivingAnyOf(Span(1, Seconds))("hello world")
      fut.map(_ => succeed)
    }
    "match from list of values" in {
      echo ! "hello world"
      val fut = receivingAnyOf("hello world", "goodbye world")
      fut.map(_ => succeed)
    }
    "match none from list of values" in {
      echo ! "hello world"
      val futureEx = recoverToExceptionIf[ExecutionException] {
        receivingAnyOf("goodbye world")
      }
      futureEx.map(ex => Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }
  "assertingReceiveAnyOf" should {
    "async send back messages of same type" in {
      echo ! "hello world"
      assertingReceiveAnyOf("hello world")
    }
    "async send back messages of same type with span" in {
      echo ! "hello world"
      assertingReceiveAnyOf(Span(1, Seconds))("hello world")
    }
    "match from list of values" in {
      echo ! "hello world"
      assertingReceiveAnyOf("hello world", "goodbye world")
    }
    "match none from list of values" in {
      echo ! "hello world"
      val futureEx = recoverToExceptionIf[ExecutionException] {
        assertingReceiveAnyOf("goodbye world")
      }
      futureEx.map(ex => Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }
}


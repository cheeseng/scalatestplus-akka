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

import java.util.concurrent.ExecutionException

import akka.actor.ActorSystem
import org.scalatest.time.{Seconds, Span}


class ReceivingNSpec(system: ActorSystem) extends AsyncSpecBase(system) {

  def this() = this(ActorSystem("ExampleSpec"))

  "An Echo actor with implicit Patience" should {
    "send back 3 messages unchanged" in {
      echo ! "hello world"
      echo ! "hello world2"
      echo ! "hello world3"
      val fut = receivingN(3)
      fut.map(_ => succeed)
    }

    "fail if it expects 2 messages but receives 1" in {
      echo ! "hello world"

      recoverToExceptionIf[ExecutionException] {
        receivingN(2)
      } map { e =>
        e.getCause().getMessage shouldBe "assertion failed: timeout (3 seconds) while expecting 2 messages (got 1)"
      }
    }
  }

  "An Echo actor with explicit Span" should {
    "send back 3 messages unchanged" in {
      echo ! "hello world"
      echo ! "hello world2"
      echo ! "hello world3"
      val fut = receivingN(3, Span(1, Seconds))
      fut.map(_ => succeed)
    }
  }

  "fail if it expects 2 messages but receives 1" in {
    val timeout = 2
    echo ! "hello world"

    recoverToExceptionIf[ExecutionException] {
      receivingN(2, Span(timeout, Seconds))
    } map { e =>
      e.getCause().getMessage shouldBe s"assertion failed: timeout ($timeout seconds) while expecting 2 messages (got 1)"
    }
  }
}

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

class ReceivingSpec(system: ActorSystem) extends AsyncSpecBase(system) {

  def this() = this(ActorSystem("ReceivingSpec"))
  
  val pf = PartialFunction[Any, String] { case in@_ =>
    in match {
      case str: String => str
      case _ => throw new IllegalArgumentException
    }
  }

  "receiving" should {
    "async partial function application" in {
      echo ! "hello world"
      val fut = receiving(pf)
      fut.map(str => str shouldBe "hello world")
    }
    "async partial function application with span" in {
      echo ! "hello world"
      val fut = receiving(Span(1, Seconds))(pf)
      fut.map(str => str shouldBe "hello world")
    }
    "async partial function with wrong type" in {
      echo ! 12345
      val futureEx = recoverToExceptionIf[IllegalArgumentException] {
        receiving(pf)
      }
      futureEx.map(ex => ex.getClass shouldBe classOf[IllegalArgumentException])
    }
  }
}


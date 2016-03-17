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
import org.scalatest.time.{Seconds, Span}
import java.util.concurrent.ExecutionException

class ReceivingASpec(system: ActorSystem) extends AsyncSpecBase(system) {

  def this() = this(ActorSystem("ReceivingA"))

  case class ExpectedType()
  case class NotExpectedType()


  "receivingA" should {
    "return an Future[ExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      receivingA[ExpectedType].map{f => f should equal (ExpectedType()) }
    }
  }

  "receivingA" should {
    "return an Future[String] when String passed to the test actor" in {
      val testMessage = "Test Message"

      echo ! testMessage

      receivingA[String].map{f => f should equal (testMessage) }
    }
  }

  "receivingA" should {
    "not return an Future[NotExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      receivingA[ExpectedType].map{f => f should not equal (NotExpectedType()) }
    }
  }

  "receivingAn" should {
    "return an Future[ExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      receivingAn[ExpectedType].map{f => f should equal (ExpectedType()) }
    }
  }

  "receivingAn" should {
    "not return an Future[NotExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      receivingAn[ExpectedType].map{f => f should not equal (NotExpectedType()) }
    }
  }

  "receivingA with span" should {
    "return an Future[ExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      receivingA[ExpectedType](span).map{f => f should equal (ExpectedType()) }
    }
  }

  "receivingA with span" should {
    "not return an Future[NotExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      receivingA[ExpectedType](span).map{f => f should not equal (NotExpectedType()) }
    }
  }

  "receivingAn with span" should {
    "return an Future[ExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      receivingAn[ExpectedType](span).map{f => f should equal (ExpectedType()) }
    }
  }

  "receivingAn with span" should {
    "not return an Future[NotExpectedType] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      receivingAn[ExpectedType](span).map{f => f should not equal (NotExpectedType()) }
    }
  }


  "assertingReceiveA" should {
    "return an Future[Assertion] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      assertingReceiveA[ExpectedType].map{f => f should equal (succeed) }
    }
  }

  "assertingReceiveAn" should {
    "return an Future[Assertion] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      assertingReceiveAn[ExpectedType].map{f => f should equal (succeed) }
    }
  }

  "assertingReceiveA with span" should {
    "return an Future[Assertion] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      assertingReceiveA[ExpectedType](span).map{f => f should equal (succeed) }
    }
  }

  "assertingReceiveAn with span" should {
    "return an Future[Assertion] when ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      assertingReceiveAn[ExpectedType](span).map{f => f should equal (succeed) }
    }
  }


  "assertingReceiveA" should {
    "fail when expecting NotExpectedType and ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      recoverToExceptionIf[ExecutionException] {
        assertingReceiveA[NotExpectedType]
      }.map(ex =>Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }

  "assertingReceiveAn" should {
    "fail when expecting NotExpectedType and ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      recoverToExceptionIf[ExecutionException] {
        assertingReceiveAn[NotExpectedType]
      }.map(ex =>Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }

  "assertingReceiveA with span" should {
    "fail when expecting NotExpectedType and ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      recoverToExceptionIf[ExecutionException] {
        assertingReceiveA[NotExpectedType](span)
      }.map(ex =>Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }

  "assertingReceiveAn with span" should {
    "fail when expecting NotExpectedType and ExpectedType passed to the test actor" in {
      echo ! ExpectedType()

      val span = patienceConfig.timeout

      recoverToExceptionIf[ExecutionException] {
        assertingReceiveAn[NotExpectedType](span)
      }.map(ex =>Option(ex.getCause).map(cause => cause.getClass) shouldBe Some(classOf[AssertionError]))
    }
  }

}


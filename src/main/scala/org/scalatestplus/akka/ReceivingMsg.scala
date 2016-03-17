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

import java.util.concurrent.TimeUnit

import akka.testkit.{TestKit, TestKitBase}

import scala.concurrent.{duration, Future}

import org.scalatest.{AsyncTestSuite, fixture, Assertion, Succeeded}
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

/**
 * Create async versions of expectMsg, which has this signature and description:
 *
 * def expectMsg[T](d: Duration, msg: T): T
 *
 * The given message object must be received within the specified time; the object will be returned.
 */
trait ReceivingMsg extends PatienceConfiguration {
  this :TestKitBase with AsyncTestSuite =>

  import org.scalatest.Assertions._

  import duration._

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

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

import akka.testkit.TestKitBase
import org.scalatest.AsyncTestSuite

import scala.concurrent.Future

import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

import scala.concurrent.duration.Duration

/**
 * Create async versions of receiveN, which has this signature and description:
 *
 * def receiveN(n: Int, d: Duration): Seq[AnyRef]
 *
 * n messages must be received within the given time; the received messages are returned.
 *
 * Please implement two methods, with these signatures:
 *
 * def receivingN[T](n: Int)(implicit config: PatienceConfig): Future[Seq[Any]]
 * def receivingN[T](n: Int)(timeout: Span): Future[Seq[Any]]
 *
 * Note: I'm not sure why receiveN returns a Seq[AnyRef] instead of Seq[Any]. Try to do Future[Seq[Any]].
 * Note: The reason there's no assertingReceiveN is it doesn't seem to be useful.
 */
trait ReceivingN extends PatienceConfiguration with TestKitBase with AsyncTestSuite{

  def receivingN[T](n: Int)(implicit config: PatienceConfig): Future[Seq[Any]] = {
    Future{
      receiveN(n)
    }
  }

  def receivingN[T](n: Int, timeout: Span): Future[Seq[Any]] = {
    Future{
      receiveN(n, Duration.fromNanos(timeout.totalNanos))
    }
  }
}

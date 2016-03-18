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

import org.scalatest.{Assertion, AsyncTestSuite, Succeeded}
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
 * Create async versions of expectMsgAnyOf, which has this signature and description:
 *
 * def expectMsgAnyOf[T](d: Duration, obj: T*): T
 *
 * An object must be received within the given time, and it must be equal (compared
 * with ==) to at least one of the passed reference objects; the received object will be returned.
 */
trait ReceivingAnyOf extends PatienceConfiguration {
  
  this: AsyncTestKitLike with AsyncTestSuite =>

  def receivingAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[T] = {
    receivingAnyOf(config.timeout)(obj: _*)
  }

  def receivingAnyOf[T](timeout: Span)(obj: T*): Future[T] = {
    val fd = FiniteDuration(timeout.length, timeout.unit)
    Future { expectMsgAnyOf(fd, obj: _*) }
  }

  def assertingReceiveAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[Assertion] = {
    assertingReceiveAnyOf(config.timeout)(obj: _*)
  }

  def assertingReceiveAnyOf[T](timeout: Span)(obj: T*): Future[Assertion] = {
    receivingAnyOf(timeout)(obj: _*).map(_ => Succeeded)
  }
}


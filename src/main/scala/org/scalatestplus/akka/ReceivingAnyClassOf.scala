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

import akka.testkit.TestKit
import org.scalatest.{Assertion, AsyncTestSuite, Succeeded}
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
 * Create async versions of expectMsgAnyClassOf, which has this signature and description:
 *
 * def expectMsgAnyClassOf[T](d: Duration, obj: Class[_ <: T]*): T
 *
 * An object must be received within the given time, and it must be an instance of
 * at least one of the supplied Class objects; the received object will be returned.
 *
 * Please implement four methods, with these signatures:
 *
 * def receivingAnyClassOf[T](obj: Class[_ <: T]*)(implicit config: PatienceConfig): Future[T]
 * def receivingAnyClassOf[T](obj: Class[_ <: T]*)(timeout: Span): Future[T]
 * def assertingReceiveAnyClassOf[T](obj: Class[_ <: T]*)(implicit config: PatienceConfig): Future[Assertion]
 * def assertingReceiveAnyClassOf[T](obj: Class[_ <: T]*)(timeout: Span): Future[Assertion]
 */
trait ReceivingAnyClassOf extends PatienceConfiguration {
  
  this: AsyncTestKitLike with AsyncTestSuite =>
  
  def receivingAnyClassOf[T](classes: Class[_ <: T]*)(implicit config: PatienceConfig): Future[T] = {
    receivingAnyClassOf(config.timeout)(classes: _*)
  }

  def receivingAnyClassOf[T](timeout: Span)(classes: Class[_ <: T]*): Future[T] = Future {
    val fd = FiniteDuration(timeout.length, timeout.unit)
    expectMsgAnyClassOf(fd, classes: _*)
  }

  def assertingReceiveAnyClassOf[T](classes: Class[_ <: T]*)(implicit config: PatienceConfig): Future[Assertion] = {
    assertingReceiveAnyClassOf(config.timeout)(classes: _*)
  }

  def assertingReceiveAnyClassOf[T](timeout: Span)(classes: Class[_ <: T]*): Future[Assertion] = 
    receivingAnyClassOf(timeout)(classes: _*).map(_ => Succeeded)

}

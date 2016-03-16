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

import scala.concurrent.Future

import org.scalatest.Assertion
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

/**
 * Create async versions of expectMsgAnyOf, which has this signature and description:
 *
 * def expectMsgAnyOf[T](d: Duration, obj: T*): T
 *
 * An object must be received within the given time, and it must be equal (compared
 * with ==) to at least one of the passed reference objects; the received object will be returned.
 *
 * Please implement four methods, with these signatures:
 *
 * def receivingAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[T]
 * def receivingAnyOf[T](obj: T*)(timeout: Span): Future[T]
 * def assertingReceiveAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[Assertion]
 * def assertingReceiveAnyOf[T](obj: T*)(timeout: Span): Future[Assertion]
 *
 * (Make sure the Seq is scala.collection.immutable.Seq.)
 */
trait ReceivingAnyOf extends PatienceConfiguration {

  def receivingAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[T] = ???

  def receivingAnyOf[T](obj: T*)(timeout: Span): Future[T] = ???

  def assertingReceiveAnyOf[T](obj: T*)(implicit config: PatienceConfig): Future[Assertion] = ???

  def assertingReceiveAnyOf[T](obj: T*)(timeout: Span): Future[Assertion] = ???

}


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
 * Create async versions of expectMsgAllOf, which has this signature and description:
 *
 * def expectMsgAllOf[T](d: Duration, obj: T*): Seq[T]
 *
 * A number of objects matching the size of the supplied object array must be received within
 * the given time, and for each of the given objects there must exist at least one among the received
 * ones which equals (compared with ==) it. The full sequence of received objects is returned.
 *
 * Please implement four methods, with these signatures:
 *
 * def receivingAllOf[T](obj: T*)(implicit config: PatienceConfig): Future[Seq[T]]
 * def receivingAllOf[T](obj: T*)(timeout: Span): Future[Seq[T]]
 * def assertingReceiveAllOf[T](obj: T*)(implicit config: PatienceConfig): Future[Assertion]
 * def assertingReceiveAllOf[T](obj: T*)(timeout: Span): Future[Assertion]
 *
 * (Make sure the Seq is scala.collection.immutable.Seq.)
 */
trait ReceivingAllOf extends PatienceConfiguration {

  def receivingAllOf[T](obj: T*)(implicit config: PatienceConfig): Future[Seq[T]] = ???

  def receivingAllOf[T](obj: T*)(timeout: Span): Future[Seq[T]] = ???

  def assertingReceiveAllOf[T](obj: T*)(implicit config: PatienceConfig): Future[Assertion] = ???

  def assertingReceiveAllOf[T](obj: T*)(timeout: Span): Future[Assertion] = ???
}

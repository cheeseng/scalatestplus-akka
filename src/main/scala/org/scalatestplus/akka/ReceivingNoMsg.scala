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
 * Create async versions of expectNoMsg, which has this signature and description:
 *
 * def expectNoMsg(d: Duration)
 *
 * No message must be received within the given time. This also fails if a message has
 * been received before calling this method which has not been removed from the queue
 * using one of the other methods.
 *
 * Please implement four methods, with these signatures:
 *
 * def assertingReceiveNoMsg[T](implicit config: PatienceConfig): Future[Assertion]
 * def assertingReceiveNoMsg[T](span: Span): Future[Assertion]
 */
trait ReceivingNoMsg extends PatienceConfiguration {

  def assertingReceiveNoMsg[T](implicit config: PatienceConfig): Future[Assertion] = ???

  def assertingReceiveNoMsg[T](span: Span): Future[Assertion] = ???

}

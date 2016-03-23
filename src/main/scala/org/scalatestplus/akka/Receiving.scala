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
import scala.concurrent.duration.FiniteDuration
import org.scalatest.AsyncTestSuite
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

/**
 * Create async versions of expectMsgPF, which has this signature and description:
 * def expectMsgPF[T](d: Duration)(pf: PartialFunction[Any, T]): T
 *
 * Within the given time period, a message must be received and the given partial function
 * must be defined for that message; the result from applying the partial function to the
 * received message is returned. The duration may be left unspecified (empty parentheses are
 * required in this case) to use the deadline from the innermost enclosing within block instead.
 */
trait Receiving extends PatienceConfiguration {
  this: AsyncTestKitLike with AsyncTestSuite =>
    
  def receiving[T](pf: PartialFunction[Any, T])(implicit config: PatienceConfig): Future[T] = {
    receiving(config.timeout)(pf)
  }

  def receiving[T](timeout: Span)(pf: PartialFunction[Any, T]): Future[T] = {
    val fd = FiniteDuration(timeout.length, timeout.unit)
    Future { expectMsgPF[T](fd)(pf) }
  }
}

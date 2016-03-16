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
import org.scalatest._

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.reflect.ClassTag
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.Span

/**
 * Create async versions of expectMsgType, which has this signature and description:
 *
 * def expectMsgType[T: Manifest](d: Duration)
 *
 * An object which is an instance of the given type (after erasure) must be received within
 * the allotted time frame; the object will be returned.
 *
 * Note that this is current blocking since it calls to expectMsgType
 */
trait ReceivingA extends PatienceConfiguration { this:TestKitBase with AsyncTestSuite =>

  def receivingA[T: ClassTag](implicit config: PatienceConfig): Future[T] = {
    val fd = FiniteDuration(config.timeout.length, config.timeout.unit)
    Future(expectMsgType[T](fd))
  }
  def receivingAn[T: ClassTag](implicit config: PatienceConfig): Future[T] = {
    val fd = FiniteDuration(config.timeout.length, config.timeout.unit)
    Future(expectMsgType[T](fd))
  }

  def receivingA[T: ClassTag](span: Span): Future[T] = {
    val fd = FiniteDuration(span.length, span.unit)
    Future(expectMsgType[T](fd))
  }
  def receivingAn[T: ClassTag](span: Span): Future[T] = {
    val fd = FiniteDuration(span.length, span.unit)
    Future(expectMsgType[T](fd))
  }

  def assertingReceiveA[T: ClassTag](implicit config: PatienceConfig): Future[Assertion] = {
    val fd = FiniteDuration(config.timeout.length, config.timeout.unit)
    Future(expectMsgType[T](fd)).map(_ => succeed)
  }
  def assertingReceiveAn[T: ClassTag](implicit config: PatienceConfig): Future[Assertion] = {
    val fd = FiniteDuration(config.timeout.length, config.timeout.unit)
    Future(expectMsgType[T](fd)).map(_ => succeed)
  }

  def assertingReceiveA[T: ClassTag](span: Span): Future[Assertion] = {
    val fd = FiniteDuration(span.length, span.unit)
    Future(expectMsgType[T](fd)).map(_ => succeed)
  }
  def assertingReceiveAn[T: ClassTag](span: Span): Future[Assertion] = {
    val fd = FiniteDuration(span.length, span.unit)
    Future(expectMsgType[T](fd)).map(_ => succeed)
  }
}


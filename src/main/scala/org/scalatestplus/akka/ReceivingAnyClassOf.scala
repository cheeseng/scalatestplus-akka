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

/*
Create async versions of expectMsgAnyClassOf, which has this signature and description:

def expectMsgAnyClassOf[T](d: Duration, obj: Class[_ <: T]*): T

An object must be received within the given time, and it must be an instance of
at least one of the supplied Class objects; the received object will be returned.

Please create four methods, with these signatures:

def receivingAnyClassOf[T](obj: Class[_ <: T]*)(implicit config: PatienceConfig): Future[T]
def receivingAnyClassOf[T](obj: Class[_ <: T]*)(timeout: Span): Future[T]
def assertingReceiveAnyClassOf[T](obj: Class[_ <: T]*)(implicit config: PatienceConfig): Future[Assertion]
def assertingReceiveAnyClassOf[T](obj: Class[_ <: T]*)(timeout: Span): Future[Assertion]
*/
trait ReceivingAnyClassOf

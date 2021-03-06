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
Create async versions of expectMsg, which has this signature and description:

def expectMsg[T](d: Duration, msg: T): T

The given message object must be received within the specified time; the object will be returned.

Please create four methods, with these signatures:

def receivingMsg[T](msg: T)(implicit config: PatienceConfig): Future[T]
def receivingMsg[T](msg: T, span: Span): Future[T]
def assertReceivingMsg[T](msg: T)(implicit config: PatienceConfig): Future[Assertion]
def assertReceivingMsg[T](msg: T, span: Span): Future[Assertion]
*/
trait ReceivingMsg

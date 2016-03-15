
package org.scalatestplus.akka

import akka.testkit.TestKit
import akka.actor.ActorSystem

class AsyncTestKit(system: ActorSystem) extends TestKit(system)



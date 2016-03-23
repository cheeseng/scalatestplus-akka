package org.scalatestplus.akka

import akka.actor.ActorSystem
import akka.testkit.{TestActors, TestKit, ImplicitSender}
import org.scalatest.{AsyncWordSpecLike, BeforeAndAfterAll, Matchers}

abstract class AsyncSpecBase(system: ActorSystem) extends TestKit(system) with AsyncTestKitLike with ImplicitSender
  with AsyncWordSpecLike with Matchers with BeforeAndAfterAll {
  
  val echo = system.actorOf(TestActors.echoActorProps)

  override def afterAll() = {
    TestKit.shutdownActorSystem(system)
  }
}

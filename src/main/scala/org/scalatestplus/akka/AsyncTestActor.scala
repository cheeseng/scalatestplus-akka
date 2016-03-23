package org.scalatestplus.akka

import java.util.concurrent.BlockingDeque

import akka.actor.{DeadLetter, Actor}
import akka.testkit.TestActor
import akka.testkit.TestActor._

class AsyncTestActor(queue: BlockingDeque[TestActor.Message]) extends Actor {
  import TestActor._

  var ignore: Ignore = None

  var autopilot: AutoPilot = NoAutoPilot

  def receive = {
    case SetIgnore(ign)      ⇒ ignore = ign
    case Watch(ref)          ⇒ context.watch(ref)
    case UnWatch(ref)        ⇒ context.unwatch(ref)
    case SetAutoPilot(pilot) ⇒ autopilot = pilot
    case x: AnyRef ⇒
      autopilot = autopilot.run(sender(), x) match {
        case KeepRunning ⇒ autopilot
        case other       ⇒ other
      }
      val observe = ignore map (ignoreFunc ⇒ !ignoreFunc.applyOrElse(x, FALSE)) getOrElse true
      if (observe) {
        queue.offerLast(RealMessage(x, sender()))
      }
  }

  override def postStop() = {
    import scala.collection.JavaConverters._
    queue.asScala foreach { m ⇒ context.system.deadLetters.tell(DeadLetter(m.msg, m.sender, self), m.sender) }
  }
}
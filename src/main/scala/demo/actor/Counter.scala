package demo.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  *
  * @author Tomas Perez Molina
  */
case class Count(value: Int)
case class Stats(total: Int)
case object GetStats

class Counter extends Actor{
  var total: Int = 0

  override def receive: Receive = {
    case Count(value) => total += value
    case GetStats => sender ! Stats(total)
  }
}

object Counter extends App {
  val system = ActorSystem("mySystem")
  val counter: ActorRef = system.actorOf(Props[Counter], "counter")
  val printer: ActorRef = system.actorOf(Props[Printer], "printer")

  counter ! Count(10)
  counter ! Count(20)
  counter ! Count(5)

  counter tell (GetStats, printer)
}
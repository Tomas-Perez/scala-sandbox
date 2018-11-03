package demo.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  *
  * @author Tomas Perez Molina
  */
class Printer extends Actor{
  override def receive: Receive = {
    case msg => println(s"Msg: $msg")
  }
}

object ActorApp extends App {
  val system = ActorSystem("mySystem")
  val ref: ActorRef = system.actorOf(Props[Printer], "hello")
  ref ! "Hello"

  println(ref)
}

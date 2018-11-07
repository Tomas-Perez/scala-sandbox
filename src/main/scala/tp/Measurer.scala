package tp

import akka.actor.{Actor, ActorSystem, Props}

/**
  *
  * @author Tomas Perez Molina
  */
case class Measurement(name: String, measurement: Int)
case class GetStats(name: String)
case class Reset(name: String)
case class Stats(name: String, min: Int, max: Int, total: Int, count: Int)

class Measurer extends Actor {
  override def receive: Receive = onMessage(Map.empty)

  def onMessage(map: Map[String, List[Int]]): Receive = {

    case Measurement(name, measurement) =>
      val measurements: List[Int] = map.getOrElse(name, List.empty)
      context.become(onMessage(map + (name -> (measurement :: measurements))))

    case GetStats(name) =>
      val measurements = map.getOrElse(name, List.empty)
      sender() ! Stats(name, measurements.min, measurements.max, measurements.sum, measurements.length)

    case Reset(name) => context.become(onMessage(map - name))

  }
}

class MeasurementPrinter extends Actor {
  override def receive: Receive = {
    case stats: Stats => println(stats)
  }
}

object MeasurementApp extends App {
  val system = ActorSystem("MeasureMe")
  val measurer = system.actorOf(Props[Measurer])
  val printer = system.actorOf(Props[MeasurementPrinter])

  measurer ! Measurement("hola", 50)
  measurer ! Measurement("hola", 24)
  measurer ! Measurement("hola", 0)
  measurer ! Measurement("hola", -2)
  measurer ! Measurement("hola", -40)
  measurer.tell(GetStats("hola"), printer)
}

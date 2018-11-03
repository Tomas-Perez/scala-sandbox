package demo

/**
  *
  * @author Tomas Perez Molina
  */
case class Point2D(x: Int, y: Int)

object PointConversions {
  implicit def tupleToPoint(t: (Int, Int)): Point2D = Point2D(t._1, t._2)
  implicit def intToPoint(v: Int): Point2D = Point2D(v, v)
}

object ImplicitConversions extends App {
  import PointConversions._

  val p1: Point2D = (1, 2)
  val p2: Point2D = 5
}

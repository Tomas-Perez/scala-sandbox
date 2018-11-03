package demo

/**
  * This is crap
  * @author Tomas Perez Molina
  */
class ShitPoint(_x: Int, _y: Int) {
  require(_x >= 0)
  require(_y >= 0)

  val x: Int = _x
  val y: Int = _y

  println("Created point " + x + ", " + y)

  def add(that: ShitPoint): ShitPoint = new ShitPoint(x + that.x, y + that.y)

  override def toString: String = "Point(" + x + ", " + y + ")"
}

object ShitPoint {
  def apply(_x: Int, _y: Int): ShitPoint = new ShitPoint(_x, _y)

  def minX(p1: ShitPoint, p2: ShitPoint): ShitPoint = if(p1.x < p2.x) p1 else p2
}

/**
  * Nice
  * @param x
  * @param y
  */
case class Point(x: Int, y: Int) {
  def add(that: Point): Point = Point(x + that.x, y + that.y)
  def +(that: Point): Point = Point(x + that.x, y + that.y)
}

object PointApp extends App {
  val a = Point.apply(3, 3)
  val b = Point(3, 3)

  println(a == b)
  println(a.add(b))
  println(a + b)


  val str = a match {
    case Point(0, 0) => "origen"
    case Point(0, _) => "Eje y"
    case Point(_, 0) => "Eje x"
    case Point(x, y) => s"x: $x, y: $y"
  }

  println(str)
}

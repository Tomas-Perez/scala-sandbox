package demo

/**
  *
  * @author Tomas Perez Molina
  */
trait Shape {
  def area: Int
}

case class Rect(h: Int, w: Int) extends Shape {
  override def area: Int = h * w
}

case class Triangle(h: Int, w: Int) extends Shape {
  override def area: Int = (h * w) / 2
}

//
class ShapeContainer[T <: Shape](shape: T) {
  def area: Int = shape.area
}

object BoundsApp extends App {
  val value: ShapeContainer[Rect] = new ShapeContainer(Rect(10, 10))

  //View Bounds: anything that looks like a shape, like a shape or something
  //implicitly converted to a shape
  // def area[A<% Shape](shape: A): Int = shape.area
  def area[A](shape: A)(implicit c: A => Shape): Int = shape.area
}

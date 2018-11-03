package demo

/**
  *
  * @author Tomas Perez Molina
  */
object Objects{
  case class P(x: Int, y: Int)

  implicit val numericP: Numeric[P] = new Numeric[P] {
    override def plus(x: P, y: P): P = P(x.x + y.x, x.y + y.y)

    override def minus(x: P, y: P): P = P(x.x - y.x, x.y - y.y)

    override def times(x: P, y: P): P = P(x.x * y.x, x.y * y.y)

    override def negate(x: P): P = P(-x.x, -x.y)

    override def fromInt(x: Int): P = P(0, 0)

    override def toInt(x: P): Int = 0

    override def toLong(x: P): Long = 0

    override def toFloat(x: P): Float = 0

    override def toDouble(x: P): Double = 0

    override def compare(x: P, y: P): Int = 0
  }
}



object Aritmethic {
  trait Adder[T] {
    def zero: T

    def +(a: T, b: T): T
  }

  implicit val intAdder: Adder[Int] = new Adder[Int] {
    override def zero: Int = 0

    override def +(a: Int, b: Int): Int = a + b
  }

  implicit val doubleAdder: Adder[Double] = new Adder[Double] {
    override def zero: Double = 0

    override def +(a: Double, b: Double): Double = a + b
  }

  def superSum[T](list: List[T])(implicit adder: Adder[T]): T = {
    list.foldLeft(adder.zero)(adder.+)
  }

  def megaSum[T: Adder](list: List[T]): T = {
    val adder = implicitly[Adder[T]]
    list.foldLeft(adder.zero)(adder.+)
  }
}



object DynamicFuncs extends App{
  import Objects._
  import Aritmethic._

  val l1 = List(1, 2, 3)
  val l2 = List(1.0, 2.2, 3.0)
  val l3 = List(P(0, 1), P(2, 3))

  implicit val pAdder: Adder[P] = new Adder[P] {
    override def zero: P = P(0,0)

    override def +(a: P, b: P): P = P(a.x + b.x, a.y + b.y)
  }

  val s1: Int = superSum(l1)
  val s2: Double = superSum(l2)
  val s3: P = superSum(l3)
}

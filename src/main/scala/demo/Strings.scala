package demo

/**
  *
  * @author Tomas Perez Molina
  */

object Strings {
  implicit class Str(str: String) {
    def wow: String = str.toUpperCase + "... WOW!!!"
  }
}

object Booleans {
  implicit class Bool(bool: Boolean) {
    def and(other: Bool): Bool = if (bool) other else this
    def or(other: Bool): Bool = if (bool) this else other
    def negate(other: Bool): Bool = new Bool(!bool)
  }
}

object ImplicitClasses extends App {
  import Strings._
  import Booleans._

  val s = "Hello"

  println(s.wow)

  val b1 = true
  val b2 = false
  val b3 = true

  b1 and b2 or b3
}

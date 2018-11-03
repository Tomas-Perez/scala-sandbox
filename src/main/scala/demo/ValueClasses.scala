package demo

/**
  *
  * @author Tomas Perez Molina
  *
  * AnyVal solo puede tener un constructor con un solo parametro,
  * y solamente puedo tener una propiedad
  */
object ValueClasses extends App{

  class Meter(val value: Double) extends AnyVal {
    def +(n: Meter): Meter = new Meter(value + n.value)
  }

  val m1 = new Meter(1.2)
  val m2 = new Meter(1.3)
  val m3 = m1 + m2

  val v1 = 1.2
  val v2 = 1.3

  val v3 = v1 + v2
}

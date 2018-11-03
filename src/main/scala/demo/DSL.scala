package demo

/**
  *
  * @author Tomas Perez Molina
  */
object DSL extends App{
  import Break._

  breakable {
    for (i <- 1 to 100) {
      println(i)

      if (i == 10) {
        break
      }
    }
  }
}

object Break {
  private class BreakException extends RuntimeException
  private val bExc = new BreakException

  def breakable(op: => Unit): Unit = {
    try {
      op
    }
    catch {
      case ex: BreakException =>
    }
  }

  def break: Unit = throw bExc
}

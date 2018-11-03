package tp

/**
  *
  * @author Tomas Perez Molina
  */
object Retry {
  private class ConditionNotMetException extends RuntimeException
  private val condNotMetExc = new ConditionNotMetException

  def retryable(op: => Unit): Unit = {
    try {
      while (true) {
        op
      }
    }
    catch {
      case ex: ConditionNotMetException =>
    }
  }

  def retry(cond: Boolean): Unit = if (!cond) throw condNotMetExc
}

object RetryApp extends App{
  import Retry._

  var i = 0
  retryable {
    i = i + 1
    println(s"i = $i")
    retry (i < 10)
  }
}
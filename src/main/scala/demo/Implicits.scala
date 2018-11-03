package demo

/**
  *
  * @author Tomas Perez Molina
  */

object Logs {
  case class Conf(indent: Int, app: String)

  def printError(msg: String)(implicit conf: Conf): Unit = {
    val indentStr = " " * conf.indent
    println(s"$indentStr[ERROR] [App: ${conf.app}] $msg")
  }

  def printInfo(msg: String)(implicit conf: Conf): Unit = {
    val indentStr = " " * conf.indent
    println(s"$indentStr[INFO ] [App: ${conf.app}] $msg")
  }

  implicit val defaultValues: Conf = Conf(4, "Implicits Demo")
}

object Implicits extends App {

  import Logs._

  printInfo("Loading App")
  printInfo("Loading config...")
  printError("Ups...")

}

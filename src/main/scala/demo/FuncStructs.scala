package demo

import scala.io.StdIn
import scala.util.Try

/**
  *
  * @author Tomas Perez Molina
  *
  * Nothing es el subtipo de todos los tipos, puede ser asignado a cualquier valor
  *
  * Nil = List[Nothing]
  *
  * trait Option[T+]
  *   def map[U](f: T => U): Option[U]
  *
  * case class Some[T](value: T) extends Option[T]
  *   def map[U](f: T => U) = Some(f(value))
  *
  * case object None extends Option[Nothing]
  *   def map[U](f: T => U) = None
  */

case class User(name: String, alias: Option[String] = None)

object FuncStructs extends App{

  def options(): Unit = {
    val users: List[User] = List (

      User("Pepe"),
      User("Juan", Some("juancito")),
      User("Jose", Some("Jose88"))

    )

    //Ugly
    for (u <- users) {
      u.copy(alias = u.alias.map(_.toUpperCase))
    }

    val normalizedUsers: List[User] = users.map(
      u => u.copy(alias = u.alias.map(_.toLowerCase)
      ))

    for (u <- normalizedUsers) {
      val id = u.alias.getOrElse("undefined")

      println(s"User ${u.name} alias: $id")
    }
  }

  def readInt: Either[Exception, Int] = {
    val in: String = StdIn.readLine("Enter an integer: ")
    try {
      Right(in.toInt)
    } catch {
      case e: Exception => Left(e)
    }
  }

  def readInt2: Try[Int] = {
    val in: String = StdIn.readLine("Enter an integer: ")
    Try(in.toInt)
  }

  val value = readInt2.map(_ + 1)
  println(value)

}

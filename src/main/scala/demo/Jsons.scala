package demo

/**
  *
  * @author Tomas Perez Molina
  */

case class Book(
  title: String,
  author: String,
  categories: List[String]
)

object Jsons extends App{
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  val book = Book(
    title = "Programming in Scala",
    "Martin Odersky",
    List("programming", "scala")
  )

  val json: String = book.asJson.spaces2
  println(json)

  val Right(newBook) = decode[Book](json)

  println(book == newBook)
}

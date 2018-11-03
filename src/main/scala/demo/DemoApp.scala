package demo

import scala.io.Source

/**
  *
  * @author Tomas Perez Molina
  */
object DemoApp extends App {
  private val path = "C:\\Users\\Tomas\\projects\\anaydis\\src\\test\\resources\\books\\quijote.txt"
  val lines = Source.fromFile(path).getLines().toList
  private val words = lines.map(_.toLowerCase)
    .flatMap(_.split("[^a-z]"))
    .filter(_.nonEmpty)

  private val freq = words
    .groupBy(e => e)
    .map { case (word, list) => word -> list.length }

  private val top50 = freq.toList.sortBy(-_._2).take(50)

  top50 foreach println
}

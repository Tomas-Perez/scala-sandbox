package demo

import scala.io.Source

/**
  *
  * @author Tomas Perez Molina
  */
object Laziness extends App{
  val urls: List[String] = List(
    "blablab.org",
    "blablab.org",
    "blablab.org",
    "blablab.org",
    "blablab.com"
  )

  val content = urls
    .view //lazy
    //.par //parallel
    .filter(_.endsWith(".org"))
    .map { u =>
      println(s"Downloading $u")
      Source.fromURL(u).getLines().mkString("\n")
    }

  content.take(4).foreach { c =>
    println(c.length)
  }

}

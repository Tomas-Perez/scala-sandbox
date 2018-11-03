package demo

import scala.concurrent.Future
import scala.io.{Source, StdIn}
import scala.util.{Failure, Success}

/**
  *
  * @author Tomas Perez Molina
  */

object FutureApp extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  def downloadAsync(url: String): Future[String] = {
    Future {
      Source.fromURL(url).getLines().mkString("\n")
    }
  }

  def countChars(url: String): Future[Int] = {
    downloadAsync(url).map(_.length)
  }

//  val f1 = downloadAsync("https://scala-lang.org")
//  val f2 = downloadAsync("http://scala-android.org")
//
//  f1.onComplete {
//    case Success(v) => println(s"Success: ${v.take(20)}")
//    case Failure(e) => println(s"Failure: $e")
//  }
//
//  f2.foreach {
//    v => println(s"Value: $v")
//  }


  val c1 = countChars("https://scala-lang.org")
  val c2 = countChars("http://scala-android.org")
  val c3 = countChars("http://scala-android.org")

  //Ugly
  val result: Future[Int] = c1.flatMap { v1 =>
    c2.flatMap { v2 =>
      c3.map { v3 =>
        v1 + v2 + v3
      }
    }
  }

  //Nice
  val result2: Future[Int] = for {
    v1 <- c1
    v2 <- c2
    v3 <- c3
  } yield v1 + v2 + v3

  result2.foreach {
    total => println(total)
  }

  println("Press ENTER to complete")
  StdIn.readLine("")
}


object SyncApp extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  def downloadSync(url: String): String = {
    Source.fromURL(url).getLines().mkString("\n")
  }



  Future {
    val c1 = downloadSync("https://scala-lang.org")
    println(s"c1: ${c1.take(20)}")
  }

  Future {
    val c2 = downloadSync("http://scala-android.org")
    println(s"c2: ${c2.take(20)}")
  }

  StdIn.readLine("Press ENTER to complete\n")
}

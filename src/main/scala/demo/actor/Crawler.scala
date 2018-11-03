package demo.actor

import java.net.{MalformedURLException, URL}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import org.jsoup.Jsoup

import scala.io.Source
import scala.util.Try

/**
  *
  * @author Tomas Perez Molina
  */

//Protocol -----------------------
case class FetchURL(url: String)
case class FetchedURL(url: String, content: String)

case class Parse(url: String, html: String)
case class Parsed(url: String, page: WebPage)
case class WebPage(title: String, links: List[String])

case class Crawl(url: String, maxPages: Int)
case class FoundPage(rootUrl: String, url: String, page: WebPage)

//Actors -------------------------
class Fetcher extends Actor {
  private val workers = context.actorOf(
    RoundRobinPool(4).props(Props[FetchWorker])
  )

  override def receive: Receive = {
    case msg: FetchURL => workers.forward(msg)
  }
}


class FetchWorker extends Actor {
  override def receive: Receive = {
    case FetchURL(url) =>
      try {
        println(s"[FetchWorker] Downloading $url")
        val content = Source.fromURL(url).getLines().mkString("\n")
        sender ! FetchedURL(url, content)
      } catch {
        case e: Exception =>
      }
  }
}


class Parser extends Actor {
  private val cores = Runtime.getRuntime.availableProcessors()
  private val workers = context.actorOf(
    RoundRobinPool(cores).props(Props[ParseWorker])
  )

  override def receive: Receive = {
    case msg: Parse => workers.forward(msg)
  }
}


class ParseWorker extends Actor {
  override def receive: Receive = {
    case Parse(url, html) =>
      try {
        import scala.collection.JavaConverters._

        val current = new URL(url)
        val doc = Jsoup.parse(html)

        val links = doc
          .select("a[href]")
          .asScala
          .map(_.attr("href"))

        val urls = links.flatMap(
          l => Try(new URL(current, l)).toOption
        ).map(_.toString.takeWhile(_ != '#'))
          .distinct
          .sorted

        val page = WebPage(doc.title, urls.toList)

        sender ! Parsed(url, page)
      } catch {
        case _: MalformedURLException =>
      }
  }
}

class Crawler extends Actor {
  val fetcher: ActorRef = context.actorOf(Props[Fetcher])
  val parser: ActorRef = context.actorOf(Props[Parser])
  var root: String = _
  var originalSender: ActorRef = _

  var pending: Set[String] = Set()
  var processed: Set[String] = Set()

  override def receive: Receive = {
    case Crawl(url, maxPages) =>
      root = url
      originalSender = sender
      fetcher ! FetchURL(url)

    case FetchedURL(url, content) =>
      parser ! Parse(url, content)

    case Parsed(url, webPage) =>
      originalSender ! FoundPage(root, url, webPage)

      pending = pending ++ (webPage.links.toSet -- processed)

      pending.takeRight(10).foreach { p =>
        processed += p
        fetcher ! FetchURL(p)
      }

      pending = pending.dropRight(10)
  }
}

class CrawlerPrinter extends Actor{
  override def receive: Receive = {
    case FetchedURL(url, content) => println(s"[Printer] Fetched URL($url): ${content.take(5)}")
    case Parsed(url, page) => println(s"[Printer] Parsed $url with ${page.links.size} links")
    case FoundPage() =>
  }
}

object Fetcher extends App {
  val system = ActorSystem("crawler")
  val fetcher = system.actorOf(Props[Fetcher], "fetcher")
  val printer: ActorRef = system.actorOf(Props[CrawlerPrinter], "printer")

  fetcher.tell(FetchURL("http://scala-lang.org"), printer)
  fetcher.tell(FetchURL("http://scala-android.org"), printer)
}

object Parser extends App {
  val system = ActorSystem("crawler")
  val parser = system.actorOf(Props[Parser], "parser")
  val printer: ActorRef = system.actorOf(Props[CrawlerPrinter], "printer")

  val html = "<html><title>Hello</title><a href='about.html'></a></html>"

  parser.tell(Parse("http://scala-lang.org", html), printer)
}

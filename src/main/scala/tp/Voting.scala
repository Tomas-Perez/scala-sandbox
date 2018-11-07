package tp

import scala.io.Source

/**
  *
  * @author Tomas Perez Molina
  */
object VotingFuncs {
  case class VotingResult(state: String, district: String, candidate: String, votes: Int)

  def loadResults(fileName: String): List[VotingResult] =
    Source.fromFile(fileName).getLines().map(_.split(",")).map {
      case Array(state, district, candidate, votes) => VotingResult(state, district, candidate, votes.toInt)
    }.toList

  def totalVotes(results: List[VotingResult]): Int = results.map(_.votes).sum

  def winner(results: List[VotingResult]): String =
    results.groupBy(_.candidate).map { case (c, rs) => (c, totalVotes(rs)) }.maxBy(_._2)._1

  def bestState(results: List[VotingResult], candidate: String): String =
    results.filter(_.candidate == candidate).maxBy(_.votes).state

}

object Voting extends App {
  import VotingFuncs._

  private val results: List[VotingResult] = loadResults("voting.csv")
  println(winner(results))
  println(totalVotes(results))
  println(bestState(results, "A"))
  println(bestState(results, "B"))
  println(bestState(results, "C"))
}

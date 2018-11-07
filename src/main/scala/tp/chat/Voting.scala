package tp.chat

import scala.io.Source

/**
  *
  * @author Tomas Perez Molina
  */
object Voting extends App {
  case class VotingResult(state: String, district: String, candidate: String, votes: Int)

  def loadResults(fileName: String): Array[VotingResult] = {
    Source.fromFile(fileName).getLines().map(_.split(",")).map {
      case Array(state, district, candidate, votes) => VotingResult(state, district, candidate, votes.toInt)
    }
  }

  def totalVotes(results: List[VotingResult]): Int = results.map(_.votes).sum

  def winner(results: List[VotingResult]): String =
    results.groupBy(_.candidate).map(t => (t._1, totalVotes(t._2))).maxBy(_._2)._1

  def bestState(results: List[VotingResult], candidate: String): String =
    results.filter(_.candidate == candidate).maxBy(_.votes).state
}

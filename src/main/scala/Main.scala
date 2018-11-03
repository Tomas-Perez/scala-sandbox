/**
  *
  * @author Tomas Perez Molina
  */
object Main {
  def main(args: Array[String]): Unit = {
   List(1, 2, 3, 4) map (_ * 2) foreach {
      e =>
        println(e)
        println(e * 2)
        println(e max 5)
        println(e -> 5)
    }

    val temps = List(20, 21, 21, 29, 25, 27, 26)
    println(highestTempChange(temps))

    val phrases = List("Hola mundo", "Hoy no hay recreo", "Pepe", "Juan", "Pepe otra vez")
    println(getWords(phrases))


  }

  /**
    * Highest temperature change in the week
    */
  def highestTempChange(temps: List[Int]) : (String, Int) = {
    val days = List("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    val deltas = temps.tail zip temps map { case (a, b) => (a - b).abs }
    val daysWithDelta = days.tail zip deltas
    daysWithDelta.max
  }

  /**
    * Get all words in a list of phrases
    */
  def getWords(phrases: List[String]) : List[String] =
    phrases.map(_.toLowerCase).flatMap(_.split("\\s+")).distinct
}

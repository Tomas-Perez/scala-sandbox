package tp

/**
  *
  * @author Tomas Perez Molina
  */

object SalesFunc {
  case class Sales(date: String, amounts: List[Double])

  /**
  * Implementar funciones para obtener:
  *
  * a) La lista de días en que las ventas superaron una cantidad “x” de dinero.
  *
  * b) Los días en los que se realizaron el doble de ventas que el día anterior.
  *
  * c) Los N días consecutivos en donde se obtuvieron las mejores ventas. N debe ser un parámetro de la función.
  */
  def totalAmount(sales: Sales): Double = sales.amounts.sum

  def daysOver(amount: Double, sales: List[Sales]): List[String] =
    sales.filter(totalAmount(_) > amount).map(_.date)

  def wayBetterThanYesterday(sales: List[Sales]): List[String] =
    sales.zip(sales.tail).filter { case (s1, s2) => totalAmount(s2) > 2 * totalAmount(s1) }.map(_._2).map(_.date)

  def bestN(n: Int, sales: List[Sales]): List[String] =
    sales.sliding(n).maxBy(_.map(totalAmount).sum).map(_.date)
}

object SalesApp extends App {
  
}



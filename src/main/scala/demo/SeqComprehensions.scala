package demo

/**
  *
  * @author Tomas Perez Molina
  */
object SeqComprehensions extends App {
  val list = List(1, 2, 3, 4, 5)

  val newList = for (elem <- list if elem != 3) yield elem + 1

  val ij = for (i <- 0 until 10; j <- 0 until 10; if i != j) yield (i, j)

  println(ij)
}

package demo

/**
  *
  * @author Tomas Perez Molina
  */
trait IntTree {
  def sum: Int
}

case class IntBranch(left: IntTree, right: IntTree) extends IntTree {
  override def sum: Int = left.sum + right.sum
}

case class IntLeaf(value: Int) extends IntTree {
  override def sum: Int = value
}

object IntTreeApp extends App {
  def sum(t: IntTree): Int = t match {
    case IntBranch(l, r) => sum(l) + sum(r)
    case IntLeaf(v) => v
  }

  val tree = IntBranch(IntLeaf(1), IntLeaf(2))

  println(tree.sum)
  println(sum(tree))
}



sealed trait Tree[+T]

case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[T](value: T) extends Tree[T]



object TreeApp extends App {
  type StringTree = Tree[String]
  type StringMap[T] = Map[String, T]

  def pprint(t: Tree[Any]): Unit = t match {
    case Branch(left, right) =>
      println(s"Left: $left")
      println(s"Right: $right")
    case Leaf(v) => println(s"Value: $v")
  }

  val tree = Branch(
    Leaf(2),
    Leaf(1)
  )



  println(tree)
  pprint(tree)
}


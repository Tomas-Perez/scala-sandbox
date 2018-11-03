package tp

/**
  *
  * @author Tomas Perez Molina
  */
case class Person(name: String, age: Int, children: List[Person] = Nil) {
  def descendants: List[Person] = this::children.flatMap(_.descendants)

  def adultsAndMinors: (List[Person], List[Person]) = descendants.partition(_.age >= 18)

  def loners: List[Person] = descendants.filter(_.children == Nil)

  def twins: List[(Person, List[Person])] =
    children.zip(
      children.map(c1 =>
        children.filter(c2 =>
          c2.age == c1.age && c1 != c2
        )
      )
    ) ++ children.flatMap(_.twins)

  def notAtAllTwins: List[(Person, List[Person])] =
    children.zip(
      children.map(c1 =>
        children.filter(c2 =>
          Math.abs(c2.age - c1.age) > 4 && c1 != c2
        )
      )
    ) ++ children.flatMap(_.notAtAllTwins)

  def seasonedParents: List[Person] = descendants.filter(_.childrenAverageAge > 4)

  private def childrenAverageAge: Int = children.map(_.age).sum / children.length

  def childrenWithParentsOlderThan(parentAge: Int): List[(Person, Person)] =
    descendants.filter(_.age >= parentAge)
      .map(p => (p.children, p))
      .flatMap {
        case (cs, p) => cs.map((_, p))
      }

  def grandchildren: List[(Person, List[Person])] = descendants.map(p => (p, p.children.flatMap(_.children)))


  def prettyPrint: String = s"$name: $age"
}

object PersonApp extends App {
  val august = Person("August", 3)
  val july = Person("July", 3)
  val may = Person("May", 3)
  val maggie = Person("Maggie", 56)
  val john = Person("John", 56)
  val abel = Person("Abel", 89)
  val cain = Person("Cain", 89, List(john, maggie, may, july, august))
  val adam = Person("Adam", 100, List(cain, abel))

  val brotherlyPrint: PartialFunction[(Person, List[Person]), Unit] = {
    case (p: Person, twins: List[Person]) => println(s"${p.prettyPrint} ->   ${twins.map(_.prettyPrint).mkString(", ")}")
  }

  println("Twins")
  adam.twins.foreach(brotherlyPrint)
  println("-----------------------------")
  println("Not at all twins")
  adam.notAtAllTwins.foreach(brotherlyPrint)
  println("-----------------------------")
  println("Children with parents older than 90")
  adam.childrenWithParentsOlderThan(90).foreach {
    case (c, p) => println(s"${c.prettyPrint} -> ${p.prettyPrint}")
  }
  println("-------------------------------")
  println("Grandchildren")
  adam.grandchildren.foreach(brotherlyPrint)
}

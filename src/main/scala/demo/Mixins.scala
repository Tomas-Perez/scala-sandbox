package demo

/**
  *
  * @author Tomas Perez Molina
  */
class A {
  def value: String = "A"
}

trait T1 {
  def helloT1: String = "I'm T1"
  def hello: String = "I'm T1"
}

trait T2 {
  def helloT2: String = "I'm T2"
  def hello: String = "I'm T2"
}

class B extends T1 with T2 {
  override def hello: String = super[T1].hello
}

class Person(name: String)

trait MutableChildren {
  private var children: List[Person] = Nil

  def addChild(child: Person): Unit = children = child :: children
  def getChildren: List[Person] = children
}

trait Friendly {
  def hello(name: String): String = s"Hello $name"
}


object Mixins extends App{
  type FriendlyParent = Person with MutableChildren with Friendly
  val person = new Person("Julian") with MutableChildren with Friendly
  person.addChild(new Person("X"))
}

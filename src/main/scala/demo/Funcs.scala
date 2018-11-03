package demo

/**
  *
  * @author Tomas Perez Molina
  */
object Funcs extends App {
  def mul(n1: Int, n2: Int): Int = n1 * n2
  def mul2(n1: Int)(n2: Int): Int = n1 * n2

  mul(2, 2)

  val double = mul2(2)(_)

  println(double(2))

  val partialFunction: PartialFunction[Any, Int] = {
    case n: Int =>
      n + 2
    case s: String =>
      s.length
  }

  partialFunction(4)
  partialFunction("d")

  //Partial function only
  if(partialFunction.isDefinedAt(true)) {
    partialFunction(true)
  }

  val result = List(12, "hello", true, 4).collect {
    case n: Int => n + 2
  }

  println(result)


  def f1(p: Int): Unit = {
    println("f1.start")
    println(p)
    println(p)
    println("f1.end")
  }

  def f2(p: () => Int): Unit = {
    println("f2.start")
    println(p())
    println(p())
    println("f2.end")
  }

  def f3(p: => Int): Unit = {
    println("f3.start")
    println(p)
    println(p)
    println("f3.end")
  }

  def f4(cond: Boolean, p: => Int): Unit = {
    println("f4.start")
    if(cond) {
      println(p)
      println(p)
    }
    println("f4.end")
  }

  f1({
    print("Generating value f1: ")
    10
  })

  f2(() => {
    print("Generating value f2: ")
    10
  })

  f3({
    print("Generating value f3: ")
    10
  })

  f4(true, {
    print("Generating value f4: ")
    10
  })

  f4(false, {
    print("Generating value f4: ")
    10
  })
}

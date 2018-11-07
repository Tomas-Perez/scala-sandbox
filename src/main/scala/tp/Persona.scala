package tp

case class Persona(
  nombre: String,
  edad: Int,
  hijos: List[Persona] = Nil
) {

  /* Descendientes (incluyendo nodo actual) */
  def descendientes: List[Persona] = this :: hijos.flatMap(_.descendientes)

  def mayoresYMenores: (List[Persona], List[Persona]) =
    descendientes.partition(_.edad > 18)

  def descendientesSinHijos: List[Persona] = descendientes.filter(_.hijos.isEmpty)

  def descendientesConHijos: List[Persona] = descendientes.filter(_.hijos.nonEmpty)

  val hijosPorEdad: List[Persona] = hijos.sortBy(_.edad)

  def hijosConsecutivos: List[(Persona, Persona)] = hijosPorEdad zip hijosPorEdad.tail

  def hijosMellizos: List[(Persona, Persona)] = hijosConsecutivos filter {
    case (p1, p2) => p2.edad == p2.edad
  }

  def hijosDifCuatro: List[(Persona, Persona)] = hijosConsecutivos filter {
    case (p1, p2) => p2.edad - p1.edad > 4
  }

  def difCuatro: List[(Persona, Persona)] = {
    descendientesConHijos.flatMap(_.hijosDifCuatro)
  }

  def mellizos: List[(Persona, Persona)] = descendientesConHijos.flatMap(_.hijosMellizos)

  def promedioEdadHijos: Int = hijos.foldLeft(0)(_ + _.edad) / hijos.length

//  Alternativa de una sola pasada:

//  case class Sum(v: Int, c: Int)
//
//  def promedioEdadHijos2 = {
//    val result: Sum = hijos.foldLeft(Sum(0, 0)) {
//      case (Sum(v, c), p: Persona) => Sum(v + p.edad, c + 1)
//    }
//    result.v / result.c
//  }


  def conHijosPromEdad(prom: Int): List[Persona] =
    descendientesConHijos.filter(_.promedioEdadHijos == prom)

  def conPadresMayoresQue(edad: Int): List[Persona] = descendientes.filter(_.edad > edad)
    .flatMap(_.hijos)

  def nietos: List[Persona] = hijos.flatMap(_.hijos)


}

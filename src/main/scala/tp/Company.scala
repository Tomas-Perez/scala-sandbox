package tp

/**
  *
  * @author Tomas Perez Molina
  */
object Company {
  case class Employee(name: String, sinceYear: Int, dependents: List[Employee] = Nil)

  /**
    * Implementar funciones para obtener:
    *
    * a) Cantidad de empleados de la empresa
    * b) Lista de empleados que tienen gente a cargo
    * c) Jefes que hayan dejado pasar más de 3 años entre la contratación de 2 de sus empleados
    */

  def all(president: Employee): List[Employee] = president :: president.dependents.flatMap(all)

  def totalEmployees(president: Employee): Int = all(president).size

  def bosses(president: Employee): List[Employee] = all(president).filter(_.dependents.nonEmpty)

  def dependantsByYear(boss: Employee): List[Employee] = boss.dependents.sortBy(_.sinceYear)

  def hireDeltas(boss: Employee): List[Int] =
    dependantsByYear(boss)
      .zip(dependantsByYear(boss).tail)
      .map { case (e1, e2) => e2.sinceYear - e1.sinceYear }

  def bossesWithAHireDeltaGreaterThan(years: Int, president: Employee): List[Employee] =
    bosses(president).filter { hireDeltas(_).exists(_ > 3) }
}

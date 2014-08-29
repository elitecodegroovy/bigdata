/**
 * User: JohnLiu
 * Date: 14-7-25
 */

abstract class Shape {
  def getArea(): Int
}

class Circle(r: Int) extends Shape {
  def getArea(): Int = {
    r * r * r
  }
}

//val c = new Circle(9)
//println(c.getArea())
//val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//println(numbers.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n })
//println(numbers.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n })
//
////flap
//
//val nestedNumbers = List(List(1, 2), List(3, 4))
//println(nestedNumbers.flatten)
//println(nestedNumbers.flatMap(x => x.map(_ * 2)))
//
////Map operation
//val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
//println(extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200))
////use case statement
//println(extensions.filter({case (name, extension) => extension < 200}))
//
//val one: PartialFunction[Int, String] = { case 1 => "one" }
//println(one.isDefinedAt(1))
//println(one.isDefinedAt(2))



package com.bigdata.processing
import java.util.{ArrayList, Collection => JCollection}

import scala.collection.mutable.ListBuffer

/**
 * @author Created by JohnLiu on 2014/7/31.
 * @version 0.1.0
 *          Just buckle up! It is a fun and exciting ride.
 */

object BigData {

  def main(args: Array[String]) {
    println("--------------processing data-----------------")
    //    (new NetworkService(8999, 2)).run()
    breakFunc()
    printCurrentScalaFileName()
    importPackage()
    printList()
    doProcess()
  }

  def doProcess() {
    println(foldl(List("1", "2", "3"), "0") {
      _ + _
    })
    val jCol = new ArrayList[Int]
    (1 to 5) foreach {
      jCol.add(_)
    }
    println(jCol)
    val jtoT = new JavaToTraversable(jCol)
    println((jtoT map {
      _ * 10
    } filter {
      _ > 20
    }))

    println(Iterable(1, 2, 3, 4, 5) dropRight 2)
    println(Iterable(1, 2, 3, 4, 5) takeRight 2)

    val buf = ListBuffer(1.2, 3.4, 5.6)
    buf(2) = 100
    println(buf)
    buf.update(1, 20)
    println(buf)

    val frameworks = scala.collection.mutable.Set("Lift", "Akka", "Playframework", "Scalaz")
    println(frameworks contains "Lift")
    println(frameworks contains "Scala")
    frameworks += "Scalacheck"
    println(frameworks)
    frameworks.clear() //just for mutable set
    println(frameworks contains "Lift")

    Set(1, 2, 3) ++ Set(3, 4, 5)
    val sortedNum = scala.collection.immutable.SortedSet(1, 9, 3) ++ scala.collection.immutable.SortedSet(3, 5, 4)
    println(sortedNum) //TreeSet(1, 3, 4, 5, 9)

    //map
    val mapSeq = Map((1, "1st"), (2, "2nd"))
    val mapSign = Map(1 -> "1st", 2 -> "2nd")
    //To get all the keys and values from the Map, you can use m.keys and m.values
    println(mapSeq.keys + ", " + mapSign.values)
    println(mapSeq filter { (x) => x._1 == 2})
    println(mapSign filter { (x) => x._2 equals ("2nd")})
    for (t <- mapSeq; if (t._2 equals ("1st"))) println(t)

    val artistsWithAlbums = List(
      ArtistWithAlbums(Artist("Pink Floyd", "Rock"),
        List("Dark side of the moon", "Wall")),
      ArtistWithAlbums(Artist("Led Zeppelin", "Rock"),
        List("Led Zeppelin IV", "Presence")),
      ArtistWithAlbums(Artist("Michael Jackson", "Pop"),
        List("Bad", "Thriller")),
      ArtistWithAlbums(Artist("Above & Beyond", "trance"),
        List("Tri-State", "Sirens of the Sea"))
    )
    val specialAritist = for {ArtistWithAlbums(artist, albums) <- artistsWithAlbums
                              album <- albums
                              if (artist.genre == "Rock")
    } yield album
    println(specialAritist) //List(Dark side of the moon, Wall, Led Zeppelin IV, Presence)
  }

  def breakFunc() {
    val env = System.getenv("SCALA_HOME")
    if (env == null) {
      //equal Unit
      println("Set the Scala home enviroment")
    }
    else {
      println("found scala home lets" + env + " do the real work")
    }
  }

  def printList() {
    val list = List(1, 2, 3)
    var go = true
    val x = for (i <- list; if (go)) yield {
      go = false
      i
    }
    println(x)
    val artists = Map("Pink Floyd" -> "Rock", "Led Zeppelin" -> "Rock",
      "Michael Jackson" -> "Pop", "Above & Beyond" -> "Trance")
    println(artists.get("Pink Floyd"))
    println(artists.get("Michael "))
  }

  def printCurrentScalaFileName() {
    val files = new java.io.File("./Scala-data/src/main/scala/com/bigdata/processing").listFiles
    println("----------------------------------------")
    for (file <- files) {
      if (!file.isDirectory) {
        print("FileName : " + file.getName + " and related path :");
        val fileName = file.getName
        if (fileName.endsWith(".scala")) println(file)
      }
    }
  }

  def importPackage() {
    //import class in closure bracket
    val randomValue = {
      import scala.util.Random
      new Random().nextInt
    }
    println(randomValue)
    //Implicit conversion with implicit classes
    //    pimport scala.util.Randomn(2.4) //It doesn't work.


    //implicit class
    println(new RangeMaker(1).-->(10))

    //call the type parameter
    val xs = List("one", "two", "three")
    println("two in list index value: " + position(xs, "two"))

    //position(List(), "something").getOrElse(-1)
    //position(List("one", "two", "three"), "three").getOrElse(-1)

    val yx = List(3, 6, 9, 12)
    println("two in list index value: " + position(yx, 9))

    //fold operations
    println(List(1, 2, 3, 4).foldLeft(0) {
      _ + _
    })
    //count the size of the list
    println(List(1, 2, 3, 4, 5, 6).foldLeft(0) { (a, b) => a + 1})
  }

  def double2Int(d: Double): Int = d.toInt

  //Here��s how the position function looks with type parameterization
  def position[A](xs: List[A], value: A): Int = {
    xs.indexOf(value)
  }

  /**
   * Builds function object.
   */
  object foldl {
    def apply[A, B](xs: Traversable[A], defaultValue: B)(op: (B, A) => B) =
      (defaultValue /: xs)(op)
  }

}

class RangeMaker(val left: Int) extends AnyVal {
  def -->(right: Int): Range = left to right
}

class JavaToTraversable[E](javaCollection: JCollection[E]) extends Traversable[E] {
  def foreach[U](f: E => U): Unit = {
    val iterator = javaCollection.iterator
    while (iterator.hasNext) {
      f(iterator.next)
    }
  }
}



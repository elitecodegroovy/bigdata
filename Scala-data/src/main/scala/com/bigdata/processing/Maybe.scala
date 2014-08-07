package com.bigdata.processing

/**
 * @author   JohnLiu  
 * @date     2014/8/4
 * @version  0.1.0
 */
sealed abstract class Maybe[+A] {
  def isEmpty: Boolean
  def get: A
  def getOrElse[B >: A](default: B): B = {
    if(isEmpty) default else get
  }
}

final case class Just[A](value: A) extends Maybe[A] {
  def isEmpty = false
  def get = value
}

case object Nil extends Maybe[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Nil.get")
}

package com.houjun.stockPredictor.bean

import scala.runtime.RichDouble
import scala.util.Try

/**
  * Created by houjun on 15/11/5.
  *
  * @author houjun
  */
class Money private(private val longValue: Long) {

  def +(that: Money) = new Money(this.longValue + that.longValue)

  def -(that: Money) = new Money(this.longValue - that.longValue)

  def *(m: Double) = new Money((this.longValue.toDouble * m).toLong)

  def /(m: Int) = new Money((this.longValue.toDouble / m).toLong)

  def /(that: Money) = this.longValue.toDouble / that.longValue

  override def equals(that: scala.Any): Boolean = that match {
    case that: Money => this.longValue == that.longValue
    case _ => false
  }

  def value = longValue.toDouble / 100

  override def toString: String = value.toString
}

object Money {
  val MONEY0 = new Money(0)
  val MONEY1 = new Money(100)

  def apply(baseValue: Double) = baseValue match {
    case 0.0 => MONEY0
    case 1.0 => MONEY1
    case x if x > 0 => new Money((baseValue * 100).toLong)
    case _ => MONEY0
  }

  def apply(baseValue: String): Money = this (Try(baseValue.toDouble).getOrElse(0.0))

  implicit def money2RichDouble(money: Money): RichDouble = new RichDouble(money.value)
}
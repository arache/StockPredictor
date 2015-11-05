package com.houjun.stockPredictor.bean

import scala.collection.mutable

/**
  * Created by houjun on 15/11/5.
  *
  * @author houjun
  */
class CandleStick(
                   val date: String,
                   val symbol: Symbol,
                   val stockName: String,
                   val closePrice: Money = Money.MONEY0,
                   val highPrice: Money = Money.MONEY0,
                   val lowPrice: Money = Money.MONEY0,
                   val openPrice: Money = Money.MONEY0,
                   val pClosePrice: Money = Money.MONEY0,
                   val changeValue: Money = Money.MONEY0,
                   val changeRate: Double = 0.0,
                   val turnoverRate: Double = 0.0,
                   val volume: Double = 0.0,
                   val turnover: Double = 0.0,
                   val totalMarketCapitalization: Double = 0.0,
                   val circulationMarketValue: Double = 0.0) extends Ordered[CandleStick] {

  override def toString: String = s"$date $symbol $stockName C:$closePrice H:$highPrice L:$lowPrice O:$openPrice PC:$pClosePrice CR:$changeRate"

  override def compare(that: CandleStick): Int = this.date compare that.date

  val indicators = mutable.Map[String, Double]()
}

object CandleStick {
  def apply(rawArray: String) = {
    val array = rawArray.split(",")
    new CandleStick(
      array(0), Symbol(array(1)), array(2),
      Money(array(3)), Money(array(4)), Money(array(5)), Money(array(6)), Money(array(7)), Money(array(8)),
      array(9).toDouble, array(10).toDouble, array(11).toDouble, array(12).toDouble, array(13).toDouble, array(14).toDouble)
  }
}

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
                   var closePrice: Money = Money.MONEY0,
                   var highPrice: Money = Money.MONEY0,
                   var lowPrice: Money = Money.MONEY0,
                   var openPrice: Money = Money.MONEY0,
                   var pClosePrice: Money = Money.MONEY0,
                   var changeValue: Money = Money.MONEY0,
                   var changeRate: Double = 0.0,
                   var turnoverRate: Double = 0.0,
                   var volume: Double = 0.0,
                   var turnover: Double = 0.0,
                   var totalMarketCapitalization: Double = 0.0,
                   var circulationMarketValue: Double = 0.0) extends Ordered[CandleStick] {

  override def toString: String = s"$date $symbol $stockName C:$closePrice H:$highPrice L:$lowPrice O:$openPrice PC:$pClosePrice CR:$changeRate $indicators"

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

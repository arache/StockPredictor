package com.houjun.stockPredictor.strategies

import com.houjun.stockPredictor.ConfigLoader
import com.houjun.stockPredictor.bean.{Money, CandleStick}

/**
  * Created by arache on 15/11/6.
  *
  * @author arache
  */
trait Strategy extends ConfigLoader {

  def getStrategyName: String

  def getParameterMap: Map[String, String]

  protected def loadParameters2Map: Map[String, String] = {
    val strategyConfig = (configXml \ "strategy-settings" \ "strategy").toList.filter(node => (node \ "@name").text == getStrategyName)

    strategyConfig match {
      case head :: tail =>
        (head \ "parameter").toList.map(node => (node \ "@name").text -> (node \ "@value").text).toMap
      case Nil =>
        Console.err.println("No such strategy " + getStrategyName)
        Map[String, String]()
    }
  }

  def runStrategy(candleStickList: List[CandleStick])

  def calculateIndicators(candleStickList: List[CandleStick], curveNames: Array[String]): Unit = {
    val maReg = "MA(.+)".r
    val vwapReg = "VWAP(.+)".r
    val pvwapReg = "PVWAP(.+)".r

    curveNames.foreach {
      case curveName@maReg(day) =>
        val d = day.toInt
        val ma = List.fill(d - 1)(0.0) ::: candleStickList.map(_.closePrice.value).sliding(d).map(_.sum / d).toList
        candleStickList.zip(ma).foreach(x => x._1.indicators += (curveName -> x._2))
      case curveName@vwapReg(day) =>
        val d = day.toInt
        val vwap = List.fill(d - 1)(0.0) :::
          candleStickList
            .map(x => x.turnover -> x.volume)
            .sliding(d)
            .map(z => z.foldLeft(0.0d -> 0.0)((x, y) => (x._1 + y._1) -> (x._2 + y._2)))
            .map(x => x._1 / x._2)
            .toList
        candleStickList.zip(vwap).foreach(x => x._1.indicators += (curveName -> x._2))
      case curveName@pvwapReg(day) =>
        val d = day.toInt
        val vwap = List.fill(d)(0.0) :::
          candleStickList
            .map(x => x.turnover -> x.volume)
            .sliding(d + 1)
            .map(z => z.foldLeft(-z.last._1 -> -z.last._2)((x, y) => (x._1 + y._1) -> (x._2 + y._2)))
            .map(x => x._1 / x._2)
            .toList
        candleStickList.zip(vwap).foreach(x => x._1.indicators += (curveName -> x._2))
    }
  }

}

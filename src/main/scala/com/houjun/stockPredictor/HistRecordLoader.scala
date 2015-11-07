package com.houjun.stockPredictor

import com.houjun.stockPredictor.bean.{CandleStick, Money}

import scala.io.Source

/**
  * Created by houjun on 15/11/5.
  *
  * @author houjun
  */
object HistRecordLoader {
  def loadFile(file: Source): List[CandleStick] = {
    val rawData = file.getLines().toList
      .filter(_.matches("\\d{4}-\\d{2}-\\d{2},.+"))
      .filter(!_.contains("None"))
      .map(CandleStick(_))
      .filter(_.openPrice > Money.MONEY0.value)
      .sorted
//    formatXDRecord(rawData)
    rawData
  }

  def formatXDRecord(candleStickList: List[CandleStick]): List[CandleStick] = {
    def recursiveFormat(reversedList: List[CandleStick], totalDividend: Money): Unit = {
      reversedList match {
        case today :: yesterday :: tail =>
          val dividend = if (today.stockName.startsWith("XD")) yesterday.closePrice - today.pClosePrice else totalDividend
          if (today.pClosePrice != yesterday.closePrice) {
            yesterday.closePrice -= dividend
            yesterday.highPrice -= dividend
            yesterday.lowPrice -= dividend
            yesterday.openPrice -= dividend
            yesterday.pClosePrice -= dividend
          }
          recursiveFormat(yesterday :: tail, dividend)
        case today :: tail => // do nothing for the very fist day
      }
    }

    val reversedList = candleStickList.sorted.reverse

    recursiveFormat(reversedList, Money.MONEY0)

    candleStickList

  }
}

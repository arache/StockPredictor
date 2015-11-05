package com.houjun.stockPredictor

import com.houjun.stockPredictor.bean.{CandleStick, Money}

import scala.io.Source

/**
  * Created by arache on 15/11/5.
  *
  * @author arache
  */
object HistRecordLoader {
  def loadFile(file: Source) = {
    file.getLines().toList
      .filter(_.matches("\\d{4}-\\d{2}-\\d{2},.+"))
      .map(CandleStick(_))
      .filter(_.openPrice > Money.MONEY0)
      .sorted
  }
}

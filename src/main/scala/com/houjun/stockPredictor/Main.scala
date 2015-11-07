package com.houjun.stockPredictor

import com.houjun.stockPredictor.strategies.VWAPTURNStrategy

/**
  * Created by houjun on 15/11/7.
  *
  * @author houjun
  */
object Main extends App {
  val stockFile = io.Source.fromFile("stock/601766.txt", "GB2312")

  val candlesticks = HistRecordLoader.loadFile(stockFile)

  new VWAPTURNStrategy().runStrategy(candlesticks)
}

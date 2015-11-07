package com.houjun.stockPredictor.strategies

/**
  * Created by houjun on 15/11/6.
  *
  * @author houjun
  */
abstract class AbstractStrategy(strategyName: String) extends Strategy {
  lazy val parameterMap = loadParameters2Map()

  override def getParameterMap: Map[String, String] = parameterMap

  override def getStrategyName: String = strategyName
}

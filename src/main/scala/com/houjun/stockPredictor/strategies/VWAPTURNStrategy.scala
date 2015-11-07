package com.houjun.stockPredictor.strategies

import com.houjun.stockPredictor.bean.{Money, CandleStick}

/**
  * Created by houjun on 15/11/6.
  *
  * @author houjun
  */
class VWAPTURNStrategy extends AbstractStrategy("VWAP_TURN") {

  implicit def money2Double(money: Money): Double = money.value

  var currentPossession, availablePossession = initPossession
  var currentMoney = initMoney

  override def runStrategy(candleStickList: List[CandleStick]): Unit = {
    calculateIndicators(candleStickList, Array("VWAP" + getDays))

    var xdCountDown = 0
    var previousVwap = 0.0d
    var previousDirection = "="
    var continueDays = 1

    (candleStickList ::: List(candleStickList.last)).sliding(2).map {
      case today :: tomorrow :: Nil =>
        availablePossession = currentPossession

        if (tomorrow.stockName.startsWith("XD") || tomorrow.stockName.startsWith("DR") || tomorrow.stockName.startsWith("XR")) {
          xdCountDown = getDays
          previousVwap = today.indicators("VWAP" + getDays)
          previousDirection = "="
          today + "  Next day is XD/DR/XR day   " + sellStock(today.closePrice)
        }
        else if (xdCountDown > 0) {
          xdCountDown -= 1
          previousVwap = today.indicators("VWAP" + getDays)
          previousDirection = "="
          today + "  Skip that day since it's just after XD/DR/XR day, no action"
        }
        else {
          today.indicators("VWAP" + getDays) match {
            case 0.0 =>
              previousVwap = 0.0
              previousDirection = "="
              today + "  No action today"
            case vwap if vwap >= previousVwap && previousDirection == "-" && continueDays >= getContinueDays =>
              val result = today + "  " + buyStock(today.closePrice)
              previousVwap = vwap
              previousDirection = "+"
              continueDays = 1
              result
            case vwap if vwap <= previousVwap && previousDirection == "+" && continueDays >= getContinueDays =>
              val result = today + "  " + sellStock(today.closePrice)
              previousVwap = vwap
              previousDirection = "-"
              continueDays = 1
              result
            case vwap =>
              if (vwap > previousVwap) {
                if (previousDirection == "+") continueDays += 1
                previousDirection = "+"
              }
              else if (vwap < previousVwap) {
                if (previousDirection == "-") continueDays += 1
                previousDirection = "-"
              }
              else continueDays += 1

              previousVwap = vwap
              today + "  No action today, direction=" + previousDirection
          }
        }

      case today :: Nil => // last day
        availablePossession = currentPossession
        today + "  last day"
    }.foreach(println)

  }

  private def buyStock(price: Money): String = {
    val lotsWeCanBuy = (currentMoney / price / 100).toInt - 1

    if (lotsWeCanBuy == 0) return "  No money to buy"

    val netTradeAmount = price * lotsWeCanBuy * 100

    val commission = commissionMin max Money(netTradeAmount * commissionRate)

    val grossTradeAmount = netTradeAmount + commission

    currentPossession += lotsWeCanBuy
    currentMoney -= Money(grossTradeAmount)

    s"We bought $lotsWeCanBuy stocks @$price.  " + getCurrentStatus
  }

  private def sellStock(price: Money): String = {
    val lotsWeCanSell = availablePossession

    if (lotsWeCanSell == 0) return "  No stock to sell"

    val netTradeAmount = price * lotsWeCanSell * 100

    val commission = commissionMin max Money(netTradeAmount * commissionRate)

    val grossTradeAmount = netTradeAmount - commission

    currentPossession -= lotsWeCanSell
    availablePossession = 0
    currentMoney += Money(grossTradeAmount)

    s"We sold $lotsWeCanSell stocks @$price.  " + getCurrentStatus
  }

  private def getCurrentStatus = "CurrentPossession=" + currentPossession + " CurrentMoney=" + currentMoney

  private def getDays = getParameterMap("days").toInt

  private def getContinueDays = getParameterMap("continue_days").toInt


}


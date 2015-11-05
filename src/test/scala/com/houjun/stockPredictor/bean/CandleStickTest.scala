package com.houjun.stockPredictor.bean

import org.scalatest.FunSuite

/**
  * Created by arache on 15/11/5.
  *
  * @author arache
  */
class CandleStickTest extends FunSuite {

  test("test apply") {
    val rawLine = """2015-01-14,'600000,浦发银行,15.49,15.79,15.31,15.31,15.18,0.31,2.0422,2.0708,309018855,4812472304.0,2.88942272218e+11,2.31153817775e+11"""
    val c = CandleStick(rawLine)
    assert(c.date == "2015-01-14")
    assert(c.symbol == Symbol("'600000"))
    assert(c.closePrice == Money(15.49))
    assert(c.highPrice == Money(15.79))
    assert(c.lowPrice == Money(15.31))
    assert(c.openPrice == Money(15.31))
    assert(c.pClosePrice == Money(15.18))
  }

}

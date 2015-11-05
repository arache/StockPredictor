package com.houjun.stockPredictor.bean

import org.scalatest.FunSuite

/**
  * Created by arache on 15/11/5.
  *
  * @author arache
  */
class MoneyTest extends FunSuite {

  test("apply + - * /") {
    assert(Money(0.1).value == 0.1)
    assert(Money(1.0).value == 1.0)
    assert(Money(.3).value == 0.3)
    assert(Money(50).value == 50)

    assert(Money(1.0) + Money(2.0) == Money(3.0))
    assert(Money(1.8) - Money(0.2) == Money(1.6))
    assert(Money(6.0) * 1.5 == Money(9.0))
    assert(Money(6.6) / 2 == Money(3.3))
    assert(Money.MONEY1 / 3 == Money(0.33))
  }

}

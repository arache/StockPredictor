package com.houjun.stockPredictor

import com.houjun.stockPredictor.bean.Money

import scala.xml.XML

/**
  * Created by houjun on 15/11/6.
  *
  * @author houjun
  */
trait ConfigLoader {
  val configXml = XML.load(getClass.getClassLoader.getResource("config.xml"))

  def initPossession = (configXml \ "init-settings" \ "possession" \ "@value").text.toInt

  def initMoney = Money((configXml \ "init-settings" \ "money" \ "@value").text)

  def commissionMin = Money((configXml \ "policy-settings" \ "commission" \ "@min").text)

  def commissionRate = (configXml \ "policy-settings" \ "commission" \ "@rate").text.toDouble

  def taxRate = (configXml \ "policy-settings" \ "tax" \ "@rate").text.toDouble

  def settlementDay = (configXml \ "policy-settings" \ "settlement" \ "@day").text.substring(2).toInt

}

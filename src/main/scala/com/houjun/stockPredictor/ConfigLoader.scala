package com.houjun.stockPredictor

import com.houjun.stockPredictor.bean.Money

import scala.xml.XML

/**
  * Created by arache on 15/11/6.
  *
  * @author arache
  */
trait ConfigLoader {
  val configXml = XML.load(getClass.getClassLoader.getResource("config.xml"))

  def initPossession = (configXml \ "init-settings" \ "possession" \ "@value").text

  def initMoney = Money((configXml \ "init-settings" \ "money" \ "@value").text)

  def commissionMin = Money((configXml \ "policy-settings" \ "commission" \ "@min").text)

  def commissionRate = (configXml \ "policy-settings" \ "commission" \ "@rate").text.toDouble

  def taxRate = (configXml \ "policy-settings" \ "tax" \ "@rate").text.toDouble

  def settlementDay = (configXml \ "policy-settings" \ "settlement" \ "@day").text.substring(2).toInt

}

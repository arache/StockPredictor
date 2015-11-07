package com.houjun.stockPredictor

import java.io.{IOException, FileOutputStream, InputStream}
import java.net.{URLConnection, URL}

/**
  * Created by houjun on 15/11/7.
  *
  * @author houjun
  */
object DataDownloader {
  val URL1 = "http://quotes.money.163.com/service/chddata.html?code=0"
  val URL2 = "&start="
  val URL3 = "&end="
  val URL4 = "&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP"

  def buildURL(symbol: String, startDate: String, endDate: String): String = URL1 + symbol + URL2 + startDate + URL3 + endDate + URL4

  def httpDownloader(httpURL: String, saveFile: String): Boolean = {
    var byteSum: Int = 0
    var byteRead: Int = 0
    val url: URL = new URL(httpURL)

    try {
      val conn: URLConnection = url.openConnection
      val inStream: InputStream = conn.getInputStream
      val fs: FileOutputStream = new FileOutputStream(saveFile)
      val buffer: Array[Byte] = new Array[Byte](1024)
      while ( {
        byteRead = inStream.read(buffer)
        byteRead
      } != -1) {
        byteSum += byteRead
        fs.write(buffer, 0, byteRead)
      }
      true
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        false
    }
  }

  def main(args: Array[String]) {
    val url = buildURL(args(0), args(1), args(2))
    httpDownloader(url, "stock/" + args(0) + ".txt")
  }
}

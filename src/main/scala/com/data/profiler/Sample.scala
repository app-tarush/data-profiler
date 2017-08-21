package com.data.profiler

import java.time.LocalDate

import com.data.stats.Stats
import com.github.nscala_time.time.Imports._

object Sample {

  def main(args: Array[String]): Unit = {
    val pfstats: Stats = new Stats
    pfstats.updateStatistics(12.45, 1)
    pfstats.updateStatistics("A", 1)
    val dt:Any = LocalDate.of(2017, 5, 11)
//    val dt:DateType = new DateType("2017-05-11")
    val bool = dt.isInstanceOf[LocalDate]
    println(bool)
    val date = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
    "2004-01-20" match {
      case date(year, month, day) => println(s"$year" + " " + s"$month")
    }
  }
}

package com.data.stats

import java.sql.Date

import org.apache.spark.sql.types.DateType


class Stats extends Serializable {
  var min: Either[Long, Double] = Left(Long.MaxValue)
  var max: Either[Long, Double] = Left(Long.MinValue)
  var nullCount: Long = 0
  var count: Long = 0
  var uniqueValues: Long = 0
  var empties: Long = 0
  var sum: Either[Long, Double] = Left(0)
  // adding variables for date
  var minDate: Option[Date] = None
  var maxDate: Option[Date] = None

  def updateStatistics(value: Any, counts: Long): Stats = {
    count += counts
    uniqueValues += 1
    if (isNull(value)) nullCount += 1 else value match {
      //      case _: Long =>
      //        val x = value.asInstanceOf[Long]
      //        min = Left(min.left.get.min(x))
      //        max = Left(max.left.get.max(x))
      //        sum = Left(sum.left.get + x)

      case x if x.isInstanceOf[Long] || x.isInstanceOf[Int] =>
        val x = value.toString.toLong
        min = Left(min.left.get.min(x))
        max = Left(max.left.get.max(x))
        sum = Left(sum.left.get + x)

      case _: Double =>
        val x = value.asInstanceOf[Double]
        if (min.isLeft) {
          min = Right(Double.MaxValue)
        }
        if (max.isLeft) {
          max = Right(Double.MinValue)
        }
        if (sum.isLeft) {
          sum = Right(0.0)
        }
        min = Right(min.right.get.min(x))
        max = Right(max.right.get.max(x))
        sum = Right(sum.right.get + x)

      case x if x.isInstanceOf[Date] | x.isInstanceOf[DateType] =>
        val dt = Date.valueOf(value.toString)
        if (minDate.isEmpty) {
          minDate = Some(dt)
        } else {
          if (minDate.get.after(dt)) {
            minDate = Some(dt)
          }
        }
        if (maxDate.isEmpty) {
          maxDate = Some(dt)
        } else {
          if (maxDate.get.before(dt)) {
            maxDate = Some(dt)
          }
        }

      case _: String =>
        val str = value.asInstanceOf[String]
        if (str.isEmpty) empties += 1

      case _ =>
    }
    this
  }

  def merge(interimProfStats: Stats): Unit = {
    count += interimProfStats.count
    uniqueValues += interimProfStats.uniqueValues
    nullCount += interimProfStats.nullCount
    empties += interimProfStats.empties
    min = calcMin(min, interimProfStats.min)
    max = calcMax(max, interimProfStats.max)
    sum = calcSum(sum, interimProfStats.sum)
  }

  private def isNull(any: Any): Boolean = any == null

  private def calcMin(v1: Either[Long, Double], v2: Either[Long, Double]): Either[Long, Double] = {
    if (v1.isLeft && v2.isLeft) {
      Left(v1.left.get.min(v2.left.get))
    } else {
      Right(v1.right.get.min(v2.right.get))
    }
  }

  private def calcMax(v1: Either[Long, Double], v2: Either[Long, Double]): Either[Long, Double] = {
    if (v1.isLeft && v2.isLeft) {
      Left(v1.left.get.max(v2.left.get))
    } else {
      Right(v1.right.get.max(v2.right.get))
    }
  }

  private def calcSum(v1: Either[Long, Double], v2: Either[Long, Double]): Either[Long, Double] = {
    if (v1.isLeft && v2.isLeft) {
      Left(v1.left.get + v2.left.get)
    } else {
      Right(v1.right.get + v2.right.get)
    }
  }

  override def toString: String = "min: %s, max: %s, nulls: %s, count: %s, sum: %s, unique: %s, empties: %s, minDate: %s, maxDate: %s"
    .format(min.toString, max.toString, nullCount.toString, count.toString, sum.toString, uniqueValues.toString, empties.toString, minDate.toString, maxDate.toString)
}

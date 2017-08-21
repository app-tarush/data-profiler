package com.data.profiler

import com.data.stats.StatsCache
import org.apache.spark.sql.DataFrame

class ProfileReport {

}

object ProfileReport {

  def calcStats(col: Iterator[((Int, Any), Long)]): Iterator[StatsCache] = {
    val cache = new StatsCache
    while (col.hasNext) {
      // ((colIndex, colValue), count) ---> colTuple
      val colTuple = col.next()
      cache.insertOrUpdate(colTuple._1._1, colTuple._1._2, colTuple._2)
    }
    Iterator(cache)
  }

  def run(df: DataFrame): Unit = {
    val colWiseRdd = df.rdd.
      flatMap(row => Range(0, row.length).map(idx => ((idx, row.get(idx)), 1L)).toList)
      .reduceByKey((a, b) => a + b)
    val statsCache = colWiseRdd.mapPartitions(col => calcStats(col)).reduce((stc1, stc2) => stc1.merge(stc2))
    println(statsCache.getStatsCache.get(0))
    println(statsCache.getStatsCache.get(1))
    println(statsCache.getStatsCache.get(2))
    println(statsCache.getStatsCache.get(3))
    println("trying hard")
  }

}

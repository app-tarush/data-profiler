package com.data.stats

import scala.collection.concurrent.TrieMap

class StatsCache extends Serializable {

  private val cache: TrieMap[Int, Stats] = new TrieMap[Int, Stats]

  def find(key: Int): Option[Stats] = cache.get(key)

  def isEmpty: Boolean = cache.isEmpty

  def contains(key: Int): Boolean = cache.contains(key)

  def insertOrUpdate(key: Int, value: Any, count: Long): TrieMap[Int, Stats] = {
    if (contains(key)) {
      find(key).get.updateStatistics(value, count)
    } else {
      cache.update(key, new Stats().updateStatistics(value, count))
    }
    cache
  }

  def merge(stCache: StatsCache): StatsCache = {
    stCache.getStatsCache.keySet.foreach(key => {
      val stats = cache.getOrElse(key, null)
      if (stats != null) {
        stats.merge(stCache.getStatsCache(key))
      } else {
        cache.putIfAbsent(key, stCache.getStatsCache(key))
      }
    })
    this
  }

  def getStatsCache: TrieMap[Int, Stats] = cache
}

package com.data.stats

import org.apache.spark.sql.types._

class ColumnStatsMetrics extends Serializable {

  private def getType(dataType: DataType): String = dataType match {
    case IntegerType => "Numeric"
    case DoubleType => "Numeric"
    case LongType => "Numeric"
    case IntegerType => "Numeric"
    case ShortType => "Numeric"
    case ByteType => "Numeric"
    case DateType => "Date"
    case TimestampType => "Timestamp"
    case StringType => "Categorical"
    case _ => "Unknown"
  }

}

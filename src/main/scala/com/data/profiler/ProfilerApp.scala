package com.data.profiler

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object ProfilerApp {

  def main(args: Array[String]): Unit = {
    val schema = StructType(Array(
      StructField("trn_type", StringType, true),
      StructField("trn_sec", IntegerType, true),
      StructField("trn_tm", DoubleType, true),
      StructField("trn_date", DateType, true)
    ))
    val spark = SparkSession.builder().appName("aise hi").master("local").getOrCreate()
    //    val list = List(Row.fromSeq(Seq("A", 1, 12.45)), Row.fromSeq(Seq("A", 2, 13.45)), Row.fromSeq(Seq("A", 3, 14.45)))
    //    Seq(("A", 1, 12.45), ("A", 2, 13.45), ("A", 3, 14.45))
    val frame = spark.read.format("csv")
      .option("header", "false")
      .option("delimiter", "|")
      .schema(schema)
      .load("/Users/tarushgrover/Desktop/sample.csv")
      .toDF()
    ProfileReport.run(frame)

  }

}

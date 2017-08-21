name := "data-profiler"

version := "1.0"

scalaVersion := "2.10.6"

crossScalaVersions := Seq("2.10.6", "2.11.8")

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "2.2.0",
  "org.apache.spark" % "spark-sql_2.10" % "2.2.0",
  "org.apache.spark" % "spark-catalyst_2.10" % "2.2.0",
  "org.apache.spark" % "spark-hive_2.10" % "2.2.0",
  "com.github.nscala-time" %% "nscala-time" % "2.16.0"
)

libraryDependencies += "org.apache.spark" % "spark-yarn_2.10" % "2.2.0" % "provided"


// adding this to run jobs direct on databricks cloud
//// Your username to login to Databricks Cloud
//dbcUsername := sys.env.getOrElse("DBC_USERNAME", "")
//
//// Your password (Can be set as an environment variable)
//dbcPassword := sys.env.getOrElse("DBC_PASSWORD", "")
//
//// The URL to the Databricks Cloud DB Api. Don't forget to set the port number to 34563!
//dbcApiUrl := sys.env.getOrElse ("DBC_URL", sys.error("Please set DBC_URL"))
//
//// Add any clusters that you would like to deploy your work to. e.g. "My Cluster"
//// or run dbcExecuteCommand
//dbcClusters += sys.env.getOrElse("DBC_USERNAME", "")
//
//dbcLibraryPath := s"/Users/${sys.env.getOrElse("DBC_USERNAME", "")}/lib"
        
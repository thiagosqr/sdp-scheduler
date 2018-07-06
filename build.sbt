name := "sdp-scheduler"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies := Seq(
  "com.typesafe" % "config" % "1.3.3",
  "org.json4s" %% "json4s-native" % "3.5.4",
  "org.json4s" %% "json4s-jackson" % "3.5.4",
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.h2database" % "h2" % "1.4.197",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
)

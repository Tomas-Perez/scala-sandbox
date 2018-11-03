name := "scala-sandbox"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.17"

val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

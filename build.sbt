name := "sansa"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/io.circe/circe-generic
libraryDependencies += "io.circe" %% "circe-generic" % "0.12.0-M3"
libraryDependencies += "io.circe" %% "circe-core" % "0.12.0-M3"
libraryDependencies += "io.circe" %% "circe-parser" % "0.12.0-M3"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
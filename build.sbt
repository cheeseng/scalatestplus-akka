
organization := "org.scalatestplus.akka"

name := "scalatestplus-akka"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions += "-deprecation"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0-M16-SNAP1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.0"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.0"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.0"

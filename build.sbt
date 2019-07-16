name := """playreview"""
organization := "cz.cellar"

version := "1.0-SNAPSHOT"

lazy val playreview = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += javaWs
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
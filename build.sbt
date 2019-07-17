name := """playreview"""
organization := "cz.cellar"

version := "1.0-SNAPSHOT"

lazy val playreview = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.0"


libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "com.h2database" % "h2" % "1.4.199"

// To provide an implementation of JAXB-API, which is required by Ebean.
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.1"
libraryDependencies += "javax.activation" % "activation" % "1.1.1"
libraryDependencies += "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2"
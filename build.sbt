name := """playreview"""
organization := "cz.cellar"

version := "1.0-SNAPSHOT"

lazy val playreview = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.0"


libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += ehcache
libraryDependencies += "com.h2database" % "h2" % "1.4.199"
libraryDependencies += evolutions


// To provide an implementation of JAXB-API, which is required by Ebean.
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.1"
libraryDependencies += "javax.activation" % "activation" % "1.1.1"
libraryDependencies += "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2"
libraryDependencies += "org.pac4j" % "play-pac4j_2.12" % "8.0.0"
libraryDependencies += "org.pac4j" % "pac4j-oidc" % "3.7.0" exclude("commons-io" , "commons-io")
libraryDependencies +=  "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.pac4j" % "pac4j-http" % "3.7.0"
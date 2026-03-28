import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "drink_water",
    libraryDependencies += "com.twilio.sdk" % "twilio" % "11.3.6"
  )
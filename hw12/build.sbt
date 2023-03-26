import sbt._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .aggregate(
    market,
    client
  )

lazy val slf4j = Seq(
  "org.slf4j" % "slf4j-nop" % "1.7.32"
)

lazy val test = Seq(
  "org.scalatest" %% "scalatest" % "3.2.15"
)

lazy val cats = Seq(
  "org.typelevel" %% "cats-core" % "2.9.0",
  "org.typelevel" %% "cats-effect" % "3.4.8"
)

lazy val http4s = Seq(
  "org.http4s" %% "http4s-ember-server" % "0.23.18",
  "org.http4s" %% "http4s-ember-client" % "0.23.18",
  "org.http4s" %% "http4s-dsl" % "0.23.18",
  "org.http4s" %% "http4s-circe" % "0.23.18",
  "io.circe" %% "circe-generic" % "0.14.5"
)

lazy val testcontainers = Seq(
  "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.40.12" % "it"
)

lazy val market = (project in file("market"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "market",
    libraryDependencies ++= cats ++ http4s ++ slf4j ++ test,
    dockerBaseImage := "broadinstitute/scala-baseimage:latest",
    dockerExposedPorts ++= Seq(8080),
    Docker / daemonUserUid := Option("20000"),
    Docker / daemonUser := "user",
    packageName := "market"
  )

lazy val client = (project in file("client"))
  .configs(IntegrationTest)
  .settings(
    libraryDependencies ++= cats ++ test ++ testcontainers ++ http4s ++ slf4j,
    Defaults.itSettings,
    IntegrationTest / fork := true
  )

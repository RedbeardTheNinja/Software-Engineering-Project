

import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val scalatoolsRelease = "Scala Tools Snapshot" at
  "http://scala-tools.org/repo-releases/"

  val liftVersion = "2.3"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default",
    "net.liftweb" %% "lift-mapper" % liftVersion,
    "com.h2database" % "h2" % "1.3.146"
    ) ++ super.libraryDependencies

  val postgresql = "postgresql" % "postgresql" % "9.0-801.jdbc4" % "compile"
  val widgets = "net.liftweb" %% "lift-widgets" % liftVersion
  val mapper = "net.liftweb" %% "lift-mapper" % liftVersion % "compile"
}

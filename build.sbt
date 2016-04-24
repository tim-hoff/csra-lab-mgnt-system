name := """csra-lab"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.38",
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.pac4j" % "play-pac4j" % "2.1.1-SNAPSHOT",
  "org.pac4j" % "pac4j-http" % "1.8.9-SNAPSHOT",
  "org.pac4j" % "pac4j-jwt" % "1.8.9-SNAPSHOT",
  "org.pac4j" % "pac4j-cas" % "1.8.9-SNAPSHOT",
  "org.pac4j" % "pac4j-oauth" % "1.8.9-SNAPSHOT",
  "com.typesafe.play" % "play-cache_2.11" % "2.4.0",
  "org.jadira.usertype" % "usertype.core" % "5.0.0.GA",
  "com.typesafe.play" %% "play-mailer" % "3.0.1"
 )
javacOptions ++= Seq("-g")
PlayKeys.externalizeResources := false

resolvers ++= Seq( Resolver.mavenLocal,
                "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
                "Pablo repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/")


routesGenerator := InjectedRoutesGenerator
<<<<<<< HEAD
=======




fork in run := false

fork in run := true
>>>>>>> fe4caf22feb3e67fc0e9600d25b5acfa846d7eb7

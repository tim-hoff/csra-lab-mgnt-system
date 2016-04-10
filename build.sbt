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
  "org.pac4j" % "play-pac4j" % "2.2.0-SNAPSHOT",
  "org.pac4j" % "pac4j-http" % "1.8.7",
  "org.pac4j" % "pac4j-jwt" % "1.8.7",
  "org.pac4j" % "pac4j-cas" % "1.8.7",
  "org.pac4j" % "pac4j-oauth" % "1.8.7",
  "com.typesafe.play" % "play-cache_2.11" % "2.4.0"
 )
javacOptions ++= Seq("-g")
PlayKeys.externalizeResources := false

resolvers ++= Seq( Resolver.mavenLocal,
                "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
                "Pablo repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/")


routesGenerator := InjectedRoutesGenerator



import sbt.Keys._

lazy val root = (project in file(".")).
  settings(
    name := "Kafka-Spark-Sample",
      version := "1.0",
    scalaVersion := "2.10.6",
mainClass in Compile := Some("KafkaWordCount")
  )

exportJars := true

fork := true


assemblyJarName := "Spark-Kafka-iOS.jar"

val meta = """META.INF(.)*""".r

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies  ++= Seq(
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.0",
  "org.apache.spark" % "spark-core_2.10" % "1.3.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.3.0",
  "org.apache.spark" % "spark-yarn_2.10" % "1.3.0"
)

resolvers ++= Seq(
  "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
  "Spray Repository" at "http://repo.spray.cc/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Twitter4J Repository" at "http://twitter4j.org/maven2/",
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  "Twitter Maven Repo" at "http://maven.twttr.com/",
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
  Resolver.sonatypeRepo("public")
)

    
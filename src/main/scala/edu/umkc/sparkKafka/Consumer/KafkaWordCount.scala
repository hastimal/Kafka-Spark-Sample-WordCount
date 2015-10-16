package edu.umkc.sparkKafka.Consumer


import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

/**
 * Created by Mayanka on 06-Oct-15.
 */
object KafkaWordCount {
  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }


    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
    val ssc =  new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    println("\t\tRESULTS\n\t\t------\n\t\t-------\n")
    wordCounts.print()
    val socket = iOSConnector.getSocket()
    wordCounts.foreachRDD(rdd=>
    {
      val o=rdd.collect()
      var s:String="Words:Count \n"
      o.foreach{case(word,count)=>{

        s+=word+" : "+count+"\n"

      }}

      iOSConnector.sendCommandToRobot(s, socket)
    })



    ssc.start()
    ssc.awaitTermination()
  }

}

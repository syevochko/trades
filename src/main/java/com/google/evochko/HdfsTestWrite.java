package com.google.evochko;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.examples.streaming.StreamingExamples;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

public class HdfsTestWrite {
    public static void main(String[] args) throws InterruptedException {

        saveSimpleFile("file:///D:/apache-maven-3.6.1/LICENSE");

        //saveStreamingFile("file:///d:/tmp//111");

    }

    static void saveSimpleFile(String file) {
        SparkConf conf = new SparkConf().setAppName("MarketDataIntegrating").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("file:///D:/apache-maven-3.6.1/LICENSE");
        JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);
        System.out.println(lines);
        System.out.println(totalLength);
        lines.saveAsObjectFile("hdfs://localhost:9000/user/root/input/LICENSE-" + System.currentTimeMillis() + ".txt");
    }

    static void saveStreamingFile(String file) throws InterruptedException {
        StreamingExamples.setStreamingLogLevels();

        SparkConf sparkConf = new SparkConf().setAppName("MyTestCount").setMaster("local[*]");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(60));

        JavaDStream<String> lines = ssc.textFileStream(file);
        JavaDStream<String> words = lines.flatMap((FlatMapFunction<String, String>) s ->
                Lists.newArrayList(s.split(" ")).iterator());

        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
                (PairFunction<String, String, Integer>) s ->
                        new Tuple2<>(s, 1))
                .reduceByKey((Function2<Integer, Integer, Integer>) (i1, i2) -> i1 + i2);

        wordCounts.print();
        wordCounts.saveAsHadoopFiles("hdfs://localhost:8020/user/spark/mystream/", "words-count");

        ssc.start();
        System.out.println("start awaiting moving files into directory...");
        ssc.awaitTermination();
    }
}

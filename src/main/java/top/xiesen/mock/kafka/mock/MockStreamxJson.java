package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/17 0017 10:57
 */
public class MockStreamxJson {
    private static String topic = "streamx_json";
    private static String brokerAddr = "zorkdata-95:9092";
    private static ProducerRecord<String, String> producerRecord = null;
    private static KafkaProducer<String, String> producer = null;

    public static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddr);
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        producer = new KafkaProducer<String, String>(props);
    }

    public static String buildMsg() {
        JSONObject jsonObject = new JSONObject();
        // 2020-03-08T12:35:02.659 [main] DEBUG org.apache.flink.streaming.api.graph.StreamGraphGenerator
        jsonObject.put("className", "org.apache.flink.streaming.api.graph.StreamGraphGenerator");
        jsonObject.put("methodName", "main");
        jsonObject.put("datetime", new Date().toString());
        return jsonObject.toString();
    }

    public static void send(String topic) {
        init();
        String req = buildMsg();
        producerRecord = new ProducerRecord<String, String>(
                topic,
                null,
                req
        );
        producer.send(producerRecord);
    }


    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            send(topic);
        }
    }
}

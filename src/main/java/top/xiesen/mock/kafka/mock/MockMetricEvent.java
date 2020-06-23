package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import top.xiesen.mock.kafka.pojo.MetricEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/16 0016 9:28
 */
public class MockMetricEvent {
    private static String topic = "flink-metric";
    private static String brokerAddr = "zorkdata-91:9092";
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
        producer = new KafkaProducer(props);
    }

    public static String buildMetricEvent() {
        String name = "metric";
        Long timestamp = System.currentTimeMillis();
        Map<String, Object> fields = new HashMap<>();
        fields.put("cpu_used", 0.6);
        fields.put("disk_used", 0.4);
        Map<String, String> tags = new HashMap<>();
        tags.put("hostname", "localhost");
        MetricEvent metricEvent = new MetricEvent(name, timestamp, fields, tags);
        return JSON.toJSONString(metricEvent);
    }


    public static void send() throws ExecutionException, InterruptedException {
        init();
        String req = buildMetricEvent();
        System.out.println(req);
        producerRecord = new ProducerRecord<String, String>(
                topic,
                null,
                req
        );
        producer.send(producerRecord).get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 300; i++) {
            send();
        }
    }
}

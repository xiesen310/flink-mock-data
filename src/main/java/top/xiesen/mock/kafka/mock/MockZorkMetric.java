package top.xiesen.mock.kafka.mock;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import top.xiesen.mock.kafka.avro.AvroSerializer;
import top.xiesen.mock.kafka.avro.AvroSerializerFactory;
import top.xiesen.mock.kafka.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @Description
 * @className top.xiesen.mock.kafka.mock.MockZorkMetric
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/3/15 18:15
 */
public class MockZorkMetric {
    private static String topic = "zorkdata_metric";
    private static String brokerAddr = "zorkdata-95:9092";
    private static ProducerRecord<String, byte[]> producerRecord = null;
    private static KafkaProducer<String, byte[]> producer = null;

    public static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddr);
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", ByteArraySerializer.class.getName());
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        producer = new KafkaProducer<String, byte[]>(props);
    }

    public static byte[] buildMetric() {
        Random random = new Random();
        String metricSetName = "influx_cpu";
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("hostname", "localhost");
        dimensions.put("appprogramname", "tc50");
        dimensions.put("appsystem", "TXJY");

        Map<String, Double> metrics = new HashMap<>();
        metrics.put("cpu_usage", random.nextDouble());

        AvroSerializer metricSerializer = AvroSerializerFactory.getMetricAvorSerializer();
        byte[] bytes = metricSerializer.serializingMetric(metricSetName, timestamp, dimensions, metrics);
        return bytes;
    }

    public static void send(String topic) {
        init();
        byte[] req = buildMetric();
        producerRecord = new ProducerRecord<String, byte[]>(
                topic,
                null,
                req
        );
        producer.send(producerRecord);
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 100; i++) {
            send(topic);
            Thread.sleep(1000);
        }
    }
}

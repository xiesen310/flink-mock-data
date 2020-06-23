package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import top.xiesen.mock.kafka.avro.AvroSerializer;
import top.xiesen.mock.kafka.avro.AvroSerializerFactory;
import top.xiesen.mock.kafka.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/17 0017 10:57
 */
public class MockKafkaConnect {
    //    private static String topic = "test";
    private static String topic = "streamx_sql_source";
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

    public static byte[] buildKafkaConnect() {

        String logTypeName = "tc50_biz_filebeat";
        String timestamp = DateUtil.getUTCTimeStr();
        String source = "/opt/20191231.log";
        String offset = String.valueOf(6322587L);
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("hostname", "localhost");
        dimensions.put("appprogramname", "tc50");
        dimensions.put("appsystem", "TXJY");
        Map<String, Double> measures = new HashMap<>();
        measures.put("latence", 301.0);

        Map<String, String> normalFields = new HashMap<>();
        normalFields.put("message", "成功处理");



        AvroSerializer avroSerializer = AvroSerializerFactory.getLogAvorSerializer();
        byte[] bytes = avroSerializer.serializingLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);
        return bytes;
    }

    public static void send(String topic) throws ExecutionException, InterruptedException {
        byte[] req = buildKafkaConnect();
        send(topic, req);
    }

    public static void send(String topic, byte[] msg) throws ExecutionException, InterruptedException {
        init();
        producerRecord = new ProducerRecord<String, byte[]>(
                topic,
                null,
                msg
        );
        producer.send(producerRecord);
    }


    public static void main(String[] args) throws Exception {
        for (int i = 0; i <= 100; i++) {
            String logTypeName = "tc50_biz_filebeat";
            String timestamp = DateUtil.getUTCTimeStr();
            String source = "/opt/20191231.log";
            String offset = String.valueOf(6322587L);
            Map<String, String> dimensions = new HashMap<>();
            dimensions.put("hostname", "localhost");
            dimensions.put("appprogramname", "tc50");
            dimensions.put("appsystem", "TXJY");
            Map<String, Double> measures = new HashMap<>();
            measures.put("latence", 301.0);

            Map<String, String> normalFields = new HashMap<>();
            normalFields.put("message", "成功处理");
            normalFields.put("id", String.valueOf(i));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("logTypeName", logTypeName);
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("source", source);
            jsonObject.put("offset", offset);
            jsonObject.put("dimensions", dimensions);
            jsonObject.put("measures", measures);
            jsonObject.put("normalFields", normalFields);
            System.out.println(jsonObject.toJSONString());

            AvroSerializer avroSerializer = AvroSerializerFactory.getLogAvorSerializer();
            byte[] bytes = avroSerializer.serializingLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);

            send(topic, bytes);
        }
    }
}

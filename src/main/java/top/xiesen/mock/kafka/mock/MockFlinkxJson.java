package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
public class MockFlinkxJson {
    //    private static String topic = "flinkx_json";
    private static String topic = "flinkx_json";
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


    public static void send(String topic, String msg) throws ExecutionException, InterruptedException {
        init();
        producerRecord = new ProducerRecord<String, String>(
                topic,
                null,
                msg
        );
        producer.send(producerRecord);
    }


    public static void main(String[] args) throws Exception {
        for (int i = 0; i <= 100; i++) {
            //        {"user_id":"59","name":"xs-59","id":"59","content":"xd"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "" + i);
            jsonObject.put("user_id", "" + i);
            jsonObject.put("name", "jack" + i);
            jsonObject.put("content", "xxxx");
            String json = jsonObject.toJSONString();
            send(topic, json);

        }
    }
}

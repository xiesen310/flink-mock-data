package top.xiesen.mock.kafka.mock;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import top.xiesen.mock.kafka.avro.AvroSerializer;
import top.xiesen.mock.kafka.avro.AvroSerializerFactory;
import top.xiesen.mock.kafka.utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/17 0017 10:57
 */
public class MockKafkaConnectAvroTest {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        int size = 1000;
        String topic = "info";
        String brokerAddr = "kafka-1:9092,kafka-2:9092,kafka-3:9092";

        if (args.length == 3) {
            size = Integer.valueOf(args[0]);
            topic = args[1];
            brokerAddr = args[2];
            System.out.println("请输出 topic 以及 kafka 地址");
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddr);
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", ByteArraySerializer.class.getName());
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);

        for (int i = 0; i <= size; i++) {
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

            AvroSerializer avroSerializer = AvroSerializerFactory.getLogAvorSerializer();
            byte[] bytes = avroSerializer.serializingLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);

            ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<String, byte[]>(
                    topic,
                    null,
                    bytes
            );
            KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
            producer.send(producerRecord);
            producer.close();
            long end = System.currentTimeMillis();
            System.out.println("写入 " + size + " 条数据,一共耗时 " + (end - start) + " ms");
        }
    }
}

package top.xiesen.mock.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xiesen.mock.kafka.avro.AvroSerializer;
import top.xiesen.mock.kafka.avro.AvroSerializerFactory;
import top.xiesen.mock.kafka.pojo.ZorkData;
import top.xiesen.mock.kafka.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MockProduct {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockProduct.class);
    private static String topic = "test1";
    private static String brokerAddr = "zork-poc103:9092";
    private static ProducerRecord<String, byte[]> producerRecord = null;
    private static KafkaProducer<String, byte[]> producer = null;

    public static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddr);
        props.put("client.id", "test");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", ByteArraySerializer.class.getName());
        props.put("batch.size", 1);
        producer = new KafkaProducer<String, byte[]>(props);
    }

    public static byte[] buildZorkDataResp() {
        Map<String, Object> event = new HashMap<String, Object>();
        ZorkData zorkData = new ZorkData();
        String logTypeName = "tc50_biz_filebeat";
        String timestamp = DateUtil.getUTCTimeStr();
        String source = "d:\\tc50\\log\\20191231.log";
        String offset = String.valueOf(6322587L);

        zorkData.setLogTypeName(logTypeName);
        zorkData.setOffset(offset);
        zorkData.setSource(source);
        zorkData.setTimestamp(timestamp);

        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("hostname", "ZVTDX-TC50223");
        dimensions.put("appprogramname", "ZVTDX-TC50223_770");
        dimensions.put("appsystem", "TXJY");

        Map<String, Double> measures = new HashMap<>();
        measures.put("latence", 301.0);

        Map<String, String> normalFields = new HashMap<>();
        normalFields.put("message", "成功处理");

        zorkData.setDimensions(dimensions);
        zorkData.setMeasures(measures);
        zorkData.setNormalFields(normalFields);
        String msg = JSON.toJSONString(zorkData);
        System.out.println(msg);
        AvroSerializer avroSerializer = AvroSerializerFactory.getLogAvorSerializer();
        byte[] bytes = avroSerializer.serializingLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);
        return bytes;
    }

    public static byte[] buildZorkDataReq() {
        Map<String, Object> event = new HashMap<String, Object>();
        ZorkData zorkData = new ZorkData();
        String logTypeName = "tc50_biz_filebeat_req";
        String timestamp = DateUtil.getUTCTimeStr();
        String source = "d:\\tc50\\log\\20191231.log";
        String offset = String.valueOf(6322587L);

        zorkData.setLogTypeName(logTypeName);
        zorkData.setOffset(offset);
        zorkData.setSource(source);
        zorkData.setTimestamp(timestamp);

        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("hostname", "ZVTDX-TC50223");
        dimensions.put("appprogramname", "ZVTDX-TC50223_770");
        dimensions.put("appsystem", "TXJY");

        Map<String, Double> measures = new HashMap<>();
        measures.put("latence", 301.0);

        Map<String, String> normalFields = new HashMap<>();
        normalFields.put("message", "成功处理");

        zorkData.setDimensions(dimensions);
        zorkData.setMeasures(measures);
        zorkData.setNormalFields(normalFields);
        String msg = JSON.toJSONString(zorkData);
        System.out.println(msg);
        AvroSerializer avroSerializer = AvroSerializerFactory.getLogAvorSerializer();
        byte[] bytes = avroSerializer.serializingLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);
        return bytes;
    }


    public static void send() throws ExecutionException, InterruptedException {
        init();
        byte[] req = buildZorkDataReq();
        producerRecord = new ProducerRecord<String, byte[]>(
                topic,
                null,
                req
        );

        producer.send(producerRecord).get();

        Thread.sleep(200);
        byte[] resp = buildZorkDataResp();
        producerRecord = new ProducerRecord<String, byte[]>(
                topic,
                null,
                resp
        );
        producer.send(producerRecord).get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0 ; i < 3; i++) {
            send();
        }
    }
}

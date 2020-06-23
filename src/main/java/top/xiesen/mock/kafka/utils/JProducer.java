package top.xiesen.mock.kafka.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;

/**
 * @Description 单线程实现一个 kafka 生产者客户端
 * @className top.xiesen.mock.kafka.utils.JProducer
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/4/19 21:22
 */

public class JProducer extends Thread {
    private final Logger log = LoggerFactory.getLogger(JProducer.class);

    /**
     * 配置 kafka 链接信息
     *
     * @return
     */
    public Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop:9092");
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put(ProducerConfig.ACKS_CONFIG, "1");

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    @Override
    public void run() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(configure());
        for (int i = 0; i < 100; i++) {
            JSONObject json = new JSONObject();
            json.put("id", i);
            json.put("ip", "192.168.0." + i);
            json.put("date", new Date().toString());
            String k = "key" + i;
            producer.send(new ProducerRecord<String, String>("test_kafka_game_x", k, json.toJSONString()), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e) {
                        log.error("send error,msg is " + e.getMessage());
                    } else {
                        log.info("the offset of the record we just send is: " + recordMetadata.offset());
                    }
                }
            });
        }

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            log.error("Interrupted thread error, msg is " + e.getMessage());
        }

        producer.close();
    }

    public static void main(String[] args) {
        JProducer producer = new JProducer();
        producer.start();
    }
}

package top.xiesen.mock.kafka.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 实现一个多线程生产者应用客户端
 * @className top.xiesen.mock.kafka.utils.JProducerThread
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/4/19 22:50
 */
public class JProducerThread extends Thread {
    private final Logger log = LoggerFactory.getLogger(JProducerThread.class);
    private final static int MAX_THREAD_SIZE = 6;

    public Properties configure() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop:9092");

        /**
         * 这个参数控制着相同分区内数据发送的批次个数大小，也就是当数据达到 这个size 时，进行数据发送,
         * 但是并不是数据达不到 size 的值，就不会发送数据，默认是 1048576，即 16k
         */
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        /**
         * 消息是否发送，不是仅仅通过 batch.size 的值来控制的，实际上是一种权衡策略，即吞吐量和延时之间的权衡
         * linger.ms 参数就是控制消息发送延时行为的，默认是 0，表示消息需要被立即发送。
         */
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        /**
         * 指定了producer 端用于缓存的缓存区大小，单位是字节，默认是 33554432, 即 32G
         */
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        /**
         * 用户控制 生产者的持久性 acks 有3个值，
         *  0: 表示producer 完全不理睬 broker 的处理结果
         *  all： 表示发送数据时，broker 不仅会将消息写入到本地磁盘，同时也要保证其他副本也写入完成，才返回结果
         *  1: 表示发送数据时，broker 接收到消息写入到本地磁盘即可，无需保证其他副本是否写入成功
         */
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
        // 创建一个固定线程数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_SIZE);

        // 提交任务
        executorService.submit(new JProducerThread());

        // 关闭线程
        executorService.shutdown();
    }
}

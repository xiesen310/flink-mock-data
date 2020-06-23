package top.xiesen.mock.kafka.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @className top.xiesen.mock.kafka.utils.JConsumerMutil
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/4/19 23:05
 */
public class JConsumerMutil {
    private final static Logger log = LoggerFactory.getLogger(JConsumerMutil.class);
    private final KafkaConsumer<String, String> consumer;
    private ExecutorService executorService;

    public JConsumerMutil() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ke1");
        // 开启自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        // 自动提交的间隔时间
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test_kafka_game_x"));
    }

    public void execute() {
        // 初始化线程池
        executorService = Executors.newFixedThreadPool(1);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (null != records) {
                executorService.submit(new KafkaConsumerThread(records, consumer));
            }
        }
    }

    public void shutdown() {

        try {
            if (consumer != null) {
                consumer.close();
            }

            if (executorService != null) {
                executorService.shutdown();
            }

            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.error("shutdown kafka consumer thread timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 消费者线程实例
     */
    class KafkaConsumerThread implements Runnable {

        private ConsumerRecords<String, String> records;

        public KafkaConsumerThread(ConsumerRecords<String, String> records,
                                   KafkaConsumer<String, String> consumer) {
            this.records = records;
        }

        @Override
        public void run() {
            for (TopicPartition partition : records.partitions()) {
                // 获取消费记录数据集
                List<ConsumerRecord<String, String>> partitionRecords = this.records.records(partition);
                log.info("Thread Id : " + Thread.currentThread().getId());
                for (ConsumerRecord<String, String> record : partitionRecords) {
                    System.out.println("offset =" + record.offset() + ", key=" + record.key() + ", value=" + record.value());
                }
            }
        }
    }


    public static void main(String[] args) {
        JConsumerMutil consumer = new JConsumerMutil();
        try {
            consumer.execute();
        } catch (Exception e) {
            log.error("mutil consumer from kafka has error , msg is " + e.getMessage());
            consumer.shutdown();
        }
    }
}


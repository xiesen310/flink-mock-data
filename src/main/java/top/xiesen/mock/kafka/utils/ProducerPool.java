package top.xiesen.mock.kafka.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Description
 * @className top.xiesen.mock.kafka.utils.ProducerPool
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/4/2 9:39
 */
public class ProducerPool implements Closeable {

    private CustomerProducer[] pool;

    private int threadNum = 15;
    // 轮循id
    private int index = 0;

    private static ProducerPool producerInstance = null;

    public static ProducerPool getInstance(String propertiesName) {
        if (producerInstance == null) {
            producerInstance = new ProducerPool(propertiesName);
        }
        return ProducerPool.producerInstance;
    }

    private ProducerPool(String propertiesName) {
        init(propertiesName);
    }


    public void init(String propertiesName) {
        pool = new CustomerProducer[threadNum];
        for (int i = 0; i < threadNum; i++) {
            pool[i] = new CustomerProducer(propertiesName);
        }
    }

    public CustomerProducer getProducer() {
        if (index > 65535) {
            index = 0;
        }
        return pool[index++ % threadNum];
    }

    @Override
    public void close() throws IOException {
    }
}

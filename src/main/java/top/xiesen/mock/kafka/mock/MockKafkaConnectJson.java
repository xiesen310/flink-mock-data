package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
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
public class MockKafkaConnectJson {
    private static long getSize(String propertiesName) throws Exception {
        Properties properties = PropertiesUtil.getProperties(propertiesName);
        long logSize = StringUtil.getLong(properties.getProperty("log.size", "5000").trim(), 1);
        return logSize;
    }

    private static int dataSize() {
        JSONObject jsonObject = new JSONObject();
        String className = "MockKafkaConnectJson";
        String message = "Apache Flink is an open source platform for distributed stream and batch data processing. Flink’s core is a streaming dataflow engine that provides data distribution, communication, and fault tolerance for distributed computations over data streams. Flink builds batch processing on top of the streaming engine, overlaying native iteration support, managed memory, and program optimization. This documentation is for Apache Flink version 1.10. These p";
        jsonObject.put("className", className);
        jsonObject.put("message", message);
        int length = jsonObject.toJSONString().length();
        System.out.println(length);
        System.out.println(length);
        return length;
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("请指定配置文件");
            System.exit(-1);
        }
        String propertiesName = args[0];
        long size = getSize(propertiesName);

        for (int i = 0; i <= size; i++) {
            JSONObject jsonObject = new JSONObject();
            String className = "MockKafkaConnectJson";
            String message = "Apache Flink is an open source platform for distributed stream and batch data processing. Flink’s core is a streaming dataflow engine that provides data distribution, communication, and fault tolerance for distributed computations over data streams. Flink builds batch processing on top of the streaming engine, overlaying native iteration support, managed memory, and program optimization. This documentation is for Apache Flink version 1.10. These p";
            jsonObject.put("className", className);
            jsonObject.put("message", message);
            int length = jsonObject.toJSONString().length();
            System.out.println(length);
            CustomerProducer producer = ProducerPool.getInstance(propertiesName).getProducer();
            producer.sendJsonLog(jsonObject.toJSONString());
        }
        long end = System.currentTimeMillis();
        System.out.println("写入 " + size + " 条数据,一共耗时 " + (end - start) + " ms");

    }
}

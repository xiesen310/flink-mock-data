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
public class MockKafkaConnectAvro {
    private static long getSize(String propertiesName) throws Exception {

        Properties properties = PropertiesUtil.getProperties(propertiesName);
        long logSize = StringUtil.getLong(properties.getProperty("log.size", "5000").trim(), 1);
        return logSize;
    }

    public static String sum(String logTypeName, String timestamp, String source, String offset,
                             Map<String, String> dimensions, Map<String, Double> metrics, Map<String, String> normalFields) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("logTypeName", logTypeName);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("source", source);
        jsonObject.put("offset", offset);
        jsonObject.put("dimensions", dimensions);
        jsonObject.put("metrics", metrics);
        jsonObject.put("normalFields", normalFields);
        return jsonObject.toString();
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("请指定配置文件");
            System.exit(-1);
        }
        String propertiesName = args[0];
        long size = getSize(propertiesName);

        for (int i = 0; i < size; i++) {
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
            System.out.println(sum(logTypeName, timestamp, source, offset, dimensions, measures, normalFields));
//            System.out.println("--------------------- start ----------------------------");
//            long l1 = System.currentTimeMillis();
            CustomerProducer producer = ProducerPool.getInstance(propertiesName).getProducer();
//            long l2 = System.currentTimeMillis();
//            System.out.println("获取 producer 需要的时间: " + (l2 - l1) + "ms");
            producer.sendLog(logTypeName, timestamp, source, offset, dimensions, measures, normalFields);
//            long l3 = System.currentTimeMillis();
//            System.out.println("发送数据执行的时间: " + (l3 - l2) + "ms");
//            System.out.println("--------------------- end ----------------------------");
        }
        long end = System.currentTimeMillis();
        Thread.sleep(5000);
        System.out.println("写入 " + size + " 条数据,一共耗时 " + (end - start) + " ms");
    }
}

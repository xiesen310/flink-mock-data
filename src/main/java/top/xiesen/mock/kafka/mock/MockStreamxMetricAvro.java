package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
import top.xiesen.mock.kafka.utils.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/17 0017 10:57
 */
public class MockStreamxMetricAvro {
    private static long getSize(String propertiesName) throws Exception {

        Properties properties = PropertiesUtil.getProperties(propertiesName);
        long logSize = StringUtil.getLong(properties.getProperty("log.size", "5000").trim(), 1);
        return logSize;
    }

    public static String printData(String metricSetName, String timestamp,
                                   Map<String, String> dimensions, Map<String, Double> metrics) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("metricsetname", metricSetName);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("dimensions", dimensions);
        jsonObject.put("metrics", metrics);
        return jsonObject.toString();
    }

    private static Map<String, String> getRandomDimensions() {
        Random random = new Random();
        int i = random.nextInt(10);
        Map<String, String> dimensions = new HashMap<>();

        dimensions.put("hostname", "zorkdata" + i);
        dimensions.put("ip", "192.168.1." + i);
        dimensions.put("appprogramname", "tc50");
        dimensions.put("appsystem", "tdx");

        return dimensions;
    }

    private static Map<String, Double> getRandomMetrics() {
        Map<String, Double> metrics = new HashMap<>();
        DecimalFormat df = new DecimalFormat("######0.00");
        String format = df.format(new Random().nextDouble());
        metrics.put("cpu_usage_rate", Double.valueOf(format));
        return metrics;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("请指定配置文件");
            System.exit(-1);
        }
        String propertiesName = args[0];
        long size = getSize(propertiesName);

        for (int i = 0; i < size; i++) {
            String metricSetName = "streamx_metric_avro";
            String timestamp = DateUtil.getCurrentTimestamp();
            Map<String, String> dimensions = getRandomDimensions();
            Map<String, Double> metrics = getRandomMetrics();


            System.out.println(printData(metricSetName, timestamp, dimensions, metrics));

            CustomerProducer producer = ProducerPool.getInstance(propertiesName).getProducer();
            producer.sendMetric(metricSetName, timestamp, dimensions, metrics);
            Thread.sleep(2000);
        }
        Thread.sleep(1000);
    }
}

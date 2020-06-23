package top.xiesen.mock.kafka.mock;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/17 0017 10:57
 */
public class MockConnectJsonData {
    private static String topic = "tdx3";
    private static String brokerAddr = "kafka-1:9092";
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

    /**
     * 获取当前采集时间
     *
     * @return String
     */
    private static String getLogTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        return today;
    }

    private static String getCollectTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        return today;
    }

    public static String buildMsg() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("logtypename", "tdx_filebeat");
        jsonObject.put("hostname", "kafka-connect-2");
        jsonObject.put("appprogram", "tdx");
        jsonObject.put("offset", String.valueOf(System.currentTimeMillis()));
        jsonObject.put("message", "10:06:41.335 功能请求 IP:182.140.129.3 MAC:F8A963586DFF 线程:00004364 通道ID:4 事务ID:16 请求:(0-98)集成客户校验(*) 营业部:(0001)国金证券集中交易(*)\\n66650109|************|XshR9/S5SDE=|8|0||12|7.37.0||||||||||0||0|182.140.129.3;PENGKANG;Administrator;83025;Intel(R)Core(TM)i7-4510UCPU@2.00GHz*4;bfebfbff00040651-GenuineIntel;Windows7 Service Pack 1 (Build 7601);182.140.129.3,0.0.0.0,0.0.0.0;F8A963586DFF,00FF8C535532,A0A8CD0D00B0;TF655AWJ16NG2L,143116404707;07/15/2014;8DC03929-0822-453C-A2D5-EFBE95E359BE;182.140.129.3;;NTFS;0C17-8FD7;C:;113G;HTS725050A7E630;GH2Z;TF655AWJ16NG2L;|||||2,Mar  1 2018,10:22:32|0|||GETLOGINPARAM||7.37,6.01,Mar  1 2018,10:37:07|8噝\\\\5\\\\3||||\\n10:06:41.491 调用失败 IP:182.140.129.3 MAC:F8A963586DFF 线程:00004364 通道ID:4 事务ID:16 请求:(0-98)集成客户校验(*) 营业部:(0001)国金证券集中交易(*) 耗时A:156 耗时B:0 排队:0\\n-4|资金账号或密码错误!|0|||\\n10:06:52.678 系统信息 开始关闭交易中心服务。\\n10:06:53.303 系统信息 (HS_TCP2.dll)连接守护线程退出!\\n10:06:53.335 系统信息 (HS_TCP2.dll)\\\"刷新约定购回标的证券信息\\\"线程成功退出!(记录总条数:3536)\\n10:06:54.413 系统信息 港股行情服务: 保存代码表(港股)缓存。\\n10:06:54.678 系统信息 深沪行情服务: 保存代码表缓存。\\n10:06:54.960 系统信息 交易中心服务已经成功关闭。\\n10:06:54.960 系统信息 系统正常关闭\\n");
        jsonObject.put("logdate", getLogTime());
        jsonObject.put("source", "/opt/log_TDX/20180320.log");
        jsonObject.put("collecttime", getCollectTime());
        jsonObject.put("appsystem", "tdx");
        jsonObject.put("logtimeflag", "true");
        System.out.println(jsonObject.toJSONString());
        return jsonObject.toString();
    }

    public static void send(String topic) {
        init();
        String req = buildMsg();
        producerRecord = new ProducerRecord<String, String>(
                topic,
                null,
                req
        );
        producer.send(producerRecord);
    }


    public static void main(String[] args) {
        for (int i = 0; i <= 1000; i++) {
            send(topic);
        }
    }
}

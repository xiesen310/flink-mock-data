package top.xiesen.mock.kafka.test;

import org.apache.avro.generic.GenericRecord;
import top.xiesen.mock.kafka.avro.AvroDeserializerFactory;
import top.xiesen.mock.kafka.avro.AvroSerializerFactory;
import top.xiesen.mock.kafka.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class AvroTest {
    public static void main(String[] args) {
        String logTypeName = "streamx_log_avro";
        String timestamp = DateUtil.getUTCTimeStr();
        String source = "/var/log/" + DateUtil.getDate() + ".log";
        String offset = "12345";
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("hostname", "zorkdata1");
        dimensions.put("ip", "192.168.1.1");
        dimensions.put("appprogramname", "tc50");
        dimensions.put("appsystem", "tdx");
        Map<String, Double> measures = new HashMap<>();
        Map<String, String> normalFields = new HashMap<>();
        normalFields.put("message", "data update success");
        normalFields.put("countryCode", "AM");

        byte[] bytes = AvroSerializerFactory.getLogAvorSerializer().serializingLog(logTypeName, timestamp, source,
                offset, dimensions, measures, normalFields);

        System.out.println(bytes);
        GenericRecord record = AvroDeserializerFactory.getLogsDeserializer().deserializing(bytes);
        System.out.println(record);

    }
}

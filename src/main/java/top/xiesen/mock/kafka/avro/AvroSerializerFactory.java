package top.xiesen.mock.kafka.avro;

/**
 * todo
 *
 * @version V1.0
 * @Author XieSen
 * @Date 2019/4/3 11:05.
 */
public class AvroSerializerFactory {
    private static AvroSerializer metricMetadata = null;
    private static AvroSerializer logMetadata = null;

    public static AvroSerializer getLogAvorSerializer() {
        if (logMetadata == null) {
            logMetadata = new AvroSerializer(LogAvroMacroDef.metadata);
        }
        return logMetadata;
    }


    public static AvroSerializer getMetricAvorSerializer() {
        if (metricMetadata == null) {
            metricMetadata = new AvroSerializer(MetricAvroMacroDef.metadata);
        }
        return metricMetadata;
    }
}

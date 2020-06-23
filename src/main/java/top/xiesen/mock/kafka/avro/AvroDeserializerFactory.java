package top.xiesen.mock.kafka.avro;

/**
 * todo
 *
 * @version V1.0
 * @Author XieSen
 * @Date 2019/4/3 11:01.
 */
public class AvroDeserializerFactory {
    private static AvroDeserializer logs = null;

    private static AvroDeserializer metrics = null;

    public static void init() {
        logs = null;
        metrics = null;
    }

    /**
     * getLogsDeserializer
     *
     * @return
     */
    public static AvroDeserializer getLogsDeserializer() {
        if (logs == null) {
            logs = new AvroDeserializer(LogAvroMacroDef.metadata);
        }
        return logs;
    }

    /**
     * getLogsDeserializer
     *
     * @return
     */
    public static AvroDeserializer getMetricDeserializer() {
        if (metrics == null) {
            metrics = new AvroDeserializer(MetricAvroMacroDef.metadata);
        }
        return metrics;
    }
}

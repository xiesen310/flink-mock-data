package top.xiesen.mock.kafka.avro;

/**
 * todo
 *
 * @version V1.0
 * @Author XieSen
 * @Date 2019/4/3 11:00.
 */
public class MetricAvroMacroDef {
    public static String metadata = "{\n" +
            "    \"namespace\": \"com.zork.metrics\",\n" +
            "    \"type\": \"record\",\n" +
            "    \"name\": \"metrics\",\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"name\": \"metricsetname\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"timestamp\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"dimensions\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"string\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"metrics\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"double\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}

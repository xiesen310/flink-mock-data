package top.xiesen.mock.kafka.avro;

/**
 * todo
 *
 * @version V1.0
 * @Author XieSen
 * @Date 2019/4/3 10:59.
 */
public class LogAvroMacroDef {
    public static String metadata = "{\n" +
            "    \"namespace\": \"com.zork.logs\",\n" +
            "    \"type\": \"record\",\n" +
            "    \"name\": \"logs\",\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"name\": \"logTypeName\",\n" +
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
            "            \"name\": \"source\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"offset\",\n" +
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
            "            \"name\": \"measures\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"double\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"normalFields\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"string\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

}

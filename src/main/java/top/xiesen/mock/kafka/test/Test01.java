package top.xiesen.mock.kafka.test;

/**
 * @Description
 * @className top.xiesen.mock.kafka.test.Test01
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/3/16 16:24
 */
public class Test01 {
    public static void main(String[] args) {
        String a = "CREATE TABLE myTable(\n" +
                "\tlogTypeName varchar,\n" +
                "\ttimestamp varchar,\n" +
                "\tsource varchar,\n" +
                "\toffset varchar,\n" +
                "\tlatence double,\n" +
                "\tid varchar,\n" +
                "\tmessage varchar,\n" +
                "\thostname varchar,\n" +
                "\tappprogramname varchar,\n" +
                "\tappsystem varchar\n" +
                ")WITH(\n" +
                "\ttype='kafka11',\n" +
                "\tbootstrapServers='zorkdata-95:9092',\n" +
                "\tzookeeperQuorum='zorkdata-91:2181/kafka111,zorkdata-92:2181/kafka111,zorkdata-95:2181/kafka111',\n" +
                "\tkafka.key.deserializer='org.apache.kafka.common.serialization.StringDeserializer',\n" +
                "\tkafka.value.deserializer='org.apache.kafka.common.serialization.ByteArrayDeserializer',\n" +
                "\toffsetReset='earliest',\n" +
                "\tgroupId='streamx_sql_01',\n" +
                "\ttopic='streamx_sql_source',\n" +
                "\tsourceDataType='logavro',\n" +
                "\tparallelism='1',\n" +
                "\tschemaString='hostname,appprogramname,appsystem|latence|id,message'\n" +
                ");\n" +
                "\n" +
                "create view myResult(\n" +
                "\tlatence double,\n" +
                "\tid varchar,\n" +
                "\tmessage varchar,\n" +
                "\thostname varchar,\n" +
                "\tappprogramname varchar,\n" +
                "\tappsystem varchar\n" +
                ");\n" +
                "\n" +
                "insert into myResult select latence,id,message,hostname,appprogramname,appsystem from myTable;";

        System.out.println(a.replaceAll("\n", "").replaceAll("\t", ""));
    }
}

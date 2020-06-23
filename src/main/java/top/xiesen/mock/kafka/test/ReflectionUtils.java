package top.xiesen.mock.kafka.test;

import org.apache.commons.lang.StringUtils;
import top.xiesen.mock.kafka.pojo.ZorkData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description
 * @className top.xiesen.mock.kafka.test.ReflectionUtils
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/3/11 17:06
 */
public class ReflectionUtils {
    /**
     * 构造方法私有
     */
    private ReflectionUtils() {
    }

    /**
     * 根据字段名称获取对象的属性
     *
     * @param fieldName
     * @param target
     * @return
     * @throws Exception
     */
    public static Object getFieldValueByName(String fieldName, Object target) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = target.getClass().getMethod(getter, new Class[0]);
        Object e = method.invoke(target, new Object[0]);
        return e;
    }

    /**
     * 获取所有字段名字
     *
     * @param target
     * @return
     */
    public static String[] getFiledName(Object target) throws Exception {
        Field[] fields = target.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取所有属性的值
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static Object[] getFiledValues(Object target) throws Exception {
        String[] fieldNames = getFiledName(target);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; ++i) {
            value[i] = getFieldValueByName(fieldNames[i], target);
        }
        return value;
    }


    public static String getMapKey(String s) {
        int startindex = s.indexOf("[");
        if (startindex < 0) {
            return null;
        }
        String substr = s.substring(0, startindex);
        return substr;
    }

    public static String getMapValue(String s) {
        int startindex = s.indexOf("'");
        if (startindex < 0) {
            return null;
        }
        String substr = s.substring(startindex + 1);
        int endindex = substr.indexOf("'");
        if (endindex < 0) {
            return null;
        }
        String ret = substr.substring(0, endindex);
        return ret;
    }

    public static String getPartition(ZorkData zorkData, String partition) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotEmpty(partition)) {
            List<String> list = Arrays.asList(partition.split(","));
            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i);
                if ("timestamp".equals(value)) {
                    // 日期格式化
                }
                if (value.contains("[")) {
                    String mapKey = getMapKey(value);
                    Map map = (Map) getFieldValueByName(mapKey, zorkData);
                    Object o = map.get(getMapValue(value));
                    builder.append("/" + o.toString());
                } else {
                    Object o = getFieldValueByName(value, zorkData);
                    builder.append("/" + o.toString());
                }
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        ZorkData zorkData = new ZorkData();
        zorkData.setLogTypeName("test");
        zorkData.setTimestamp(new Date().toString());
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put("appsystem", "tdx");
        zorkData.setDimensions(dimensions);

        Object logTypeName = getFieldValueByName("logTypeName", zorkData);
        System.out.println("logTypeName = " + logTypeName);

        Object dimensions1 = getFieldValueByName("dimensions", zorkData);
        System.out.println(dimensions1);
        Map map = (Map) dimensions1;

        System.out.println(map.get("appsystem"));

        String str = "logTypeName,timestamp,dimensions['appsystem']";


        System.out.println(getMapKey("dimensions['appsystem']"));
        System.out.println(getMapValue("dimensions['appsystem']"));


        String partition = getPartition(zorkData, str);
        System.out.println(partition);


    }
}

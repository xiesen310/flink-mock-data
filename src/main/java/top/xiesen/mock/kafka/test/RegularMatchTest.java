package top.xiesen.mock.kafka.test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @className top.xiesen.mock.kafka.test.RegularMatchTest
 * @Author 谢森
 * @Email xiesen@zork.com.cn
 * @Date 2020/4/13 13:08
 */
public class RegularMatchTest {
    public static void main(String[] args) {
        String str = "{id=1, name=周军锋, password=1234, create_time=2020-04-02 17:04:04, message={\"id\":\"1\",\"name\":\"周军锋\",\"password\":\"1234\",\"create_time\":\"2020-04-02 17:04:04\"}}";
        Map<String, Object> map = regularMatchMessage(str);
        //遍历选项
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        Object id = map.get("id");
        System.out.println("id: " + id);
        System.out.println("name: " + map.get("name"));
        System.out.println("password: " + map.get("password"));
        System.out.println("create_time: " + map.get("create_time"));

    }

    private static Map<String, Object> regularMatchMessage(String data) {
        Map<String, Object> map = new HashMap<>();
        String regex = "(?<=message=\\{)[^}]*(?=\\})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        String formatData;
        formatData = null;
        while (matcher.find()) {
            formatData = matcher.group();
        }

        if (null != formatData) {
            String[] split = formatData.split(",");
            for (int i = 0; i < split.length; i++) {
                String tmp = split[i];
                String[] split1 = tmp.split(":");
                String key = split1[0];
                StringBuilder str = new StringBuilder();
                for (int j = 1; j <= split1.length - 1; j++) {
                    str.append(split1[j]);
                    str.append(":");
                }
                String value = str.toString();
                map.put(key.substring(1, key.length() - 1), value.substring(1, value.length() - 2));
            }
        }
        return map;
    }
}

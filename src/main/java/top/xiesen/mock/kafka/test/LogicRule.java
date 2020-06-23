package top.xiesen.mock.kafka.test;

import top.xiesen.mock.kafka.pojo.Rule;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/13 0013 9:24
 */
public class LogicRule {
    public static String comp1(List<Rule> list, int times) {
        // 默认从大到小
        Collections.sort(list);
        List<Rule> newList = list;
        for (Rule rule : newList) {
            String operator = rule.getOperator();
            // 如果是 <= 或者 < 符号,规则数组进行翻转
            if ("<=".equals(operator) || "<=".equals(operator)) {
                Collections.reverse(newList);
            }

            if (">=".equals(operator)) {
                for (Rule r1 : newList) {
                    if (times >= Integer.parseInt(r1.getTimes())) {
                        return r1.getLevel();
                    }
                    continue;
                }
            }

            if (">".equals(operator)) {
                for (Rule r1 : newList) {
                    if (times > Integer.parseInt(r1.getTimes())) {
                        return r1.getLevel();
                    }
                    continue;
                }
            }

            if ("<=".equals(operator)) {
                for (Rule r1 : newList) {
                    if (times <= Integer.parseInt(r1.getTimes())) {
                        return r1.getLevel();
                    }
                    continue;
                }
            }

            if ("<".equals(operator)) {
                for (Rule r1 : newList) {
                    if (times < Integer.parseInt(r1.getTimes())) {
                        return r1.getLevel();
                    }
                    continue;
                }
            }

            break;
        }
        return null;
    }
}

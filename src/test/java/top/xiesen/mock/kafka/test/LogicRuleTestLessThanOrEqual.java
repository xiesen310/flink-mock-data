package top.xiesen.mock.kafka.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import top.xiesen.mock.kafka.pojo.Rule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @description: 测试小于等于
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/13 0013 10:21
 */
@RunWith(value = SpringRunner.class)
public class LogicRuleTestLessThanOrEqual {
    List<Rule> rules = new ArrayList<>();

    @Before
    public void before() {
        Rule r1 = new Rule();
        r1.setLevel("3");
        r1.setOperator("<=");
        r1.setTimes("30");

        Rule r2 = new Rule();
        r2.setLevel("2");
        r2.setOperator("<=");
        r2.setTimes("20");

        Rule r3 = new Rule();
        r3.setLevel("1");
        r3.setOperator("<=");
        r3.setTimes("10");

        rules.add(r2);
        rules.add(r1);
        rules.add(r3);

        for (Rule rule : rules) {
            System.out.println(rule.getOperator() + " " + rule.getTimes() + " " + rule.getLevel());
        }
    }

    @Test
    public void whenLessThanOrEqualIsOne() {
        String result = LogicRule.comp1(rules, 10);
        assertEquals("1", result);
        System.out.println(result);
    }

    @Test
    public void whenLessThanOrEqualIsTwo() {
        String result = LogicRule.comp1(rules, 19);
        assertEquals("2", result);
        System.out.println(result);
    }

    @Test
    public void whenLessThanOrEqualIsThree() {
        String result = LogicRule.comp1(rules, 25);
        assertEquals("3", result);
        System.out.println(result);
    }

    @Test
    public void whenThanOrEqualIsNull() {
        String result = LogicRule.comp1(rules, 31);
        assertEquals(null, result);
        System.out.println(result);
    }
}

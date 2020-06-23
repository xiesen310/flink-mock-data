package top.xiesen.mock.kafka.pojo;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/13 0013 10:19
 */
public class Rule implements Comparable<Rule> {
    private String operator;
    private String times;
    private String level;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 从大到小
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Rule o) {
        return Integer.parseInt(o.times) - Integer.parseInt(this.times);
    }

}

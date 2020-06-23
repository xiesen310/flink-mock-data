package top.xiesen.mock.kafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description:
 * @author: 谢森
 * @Email xiesen@zork.com.cn
 * @time: 2020/1/16 0016 9:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricEvent {
    /**
     * Metric name
     */
    private String name;

    /**
     * Metric timestamp
     */
    private Long timestamp;

    /**
     * Metric fields
     */
    private Map<String, Object> fields;

    /**
     * Metric tags
     */
    private Map<String, String> tags;
}

package com.bird.service.common.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shaojie
 */
@Setter@Getter
@ConfigurationProperties("bird.service.db-field-trace")
public class FieldTraceProperties {

    /**
     * 是否启用, 默认启用
     */
    private boolean enabled = true;
    /**
     * 记录周期, 默认5秒
     */
    private int interval = 5000;
    /**
     * 记录阈值, 默认1000
     */
    private int threshold = 1000;
    /**
     * 模式, 默认是混合模式
     */
    private int mode = FieldTraceExchanger.MODE_MIXED;
    /**
     * 线程名称, 默认Table-Field-Trace
     */
    private String threadName = "Table-Field-Trace";

}
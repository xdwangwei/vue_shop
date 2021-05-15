package com.vivi.vue.shop.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangwei
 * 2021/2/11 9:51
 */
@ConfigurationProperties(prefix = "thread.pool.config")
@Component
@Data
@ToString
public class ThreadPoolProperties {

    private int coreSize = 5;

    private int maximumSize = 20;

    private long keepAliveTime = 10;

    private int blockQueueSize = 200;
}

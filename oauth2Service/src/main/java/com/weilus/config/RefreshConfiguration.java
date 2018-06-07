package com.weilus.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;

/**
 * 当配置中心发生变化时，自动变更相关配置
 * Created by liutq on 2018/6/7.
 */
@Configuration
@RefreshScope
public class RefreshConfiguration {

    @Value("${spring.redis.pool.max-idle:8}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle:0}")
    private int minIdle;
    @Value("${spring.redis.pool.max-active:8}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait:-1}")
    private int maxWait;

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMaxTotal(maxActive);
        JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
        return factory;
    }

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

//    @Bean
//    @Primary
//    @RefreshScope
//    @ConfigurationProperties(prefix = "spring.rabbitmq")
//    public RabbitProperties rabbitProperties() {
//        return new RabbitProperties();
//    }

}

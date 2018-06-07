package com.weilus.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;

/**
 * 当配置中心发生变化时，自动变更相关配置
 * Created by liutq on 2018/6/7.
 */
@Configuration
public class RefreshConfiguration {


//    @Bean
//    @Primary
//    @RefreshScope
//    @ConfigurationProperties(prefix = "spring.redis.pool")
//    public JedisPoolConfig jedisPoolConfig(){
//        return new JedisPoolConfig();
//    }

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig cofnig  = new JedisPoolConfig();
        cofnig.setMaxWaitMillis(20000L);
        return new JedisConnectionFactory(cofnig);
    }

    @Bean
    public RedisTemplate<String,Object> configRedisTemplate1(JedisConnectionFactory factory){
        final RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(factory);
        return template;
    }

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public RabbitProperties rabbitProperties() {
        return new RabbitProperties();
    }

}

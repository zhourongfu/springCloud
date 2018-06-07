package serviceCustomer.config;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 当配置中心发生变化时，自动变更相关配置
 * Created by liutq on 2018/6/7.
 */
@Configuration
public class RefreshConfiguration {

    @Bean
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public RabbitProperties rabbitProperties() {
        return new RabbitProperties();
    }

}

package com.jasmine.config;

import com.jasmine.pojo.Dish;
import com.jasmine.pojo.Order;
import com.jasmine.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Dish> redisTemplateDish(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Dish> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, Order> redisTemplateOrder(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Order> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, User> redisTemplateUser(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}


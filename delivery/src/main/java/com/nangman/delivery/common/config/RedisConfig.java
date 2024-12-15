package com.nangman.delivery.common.config;

import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> shipperRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        //TODO: UUID -> ShipperDto 로 변경
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // TODO: JAVA로 변경
        redisTemplate.setValueSerializer(RedisSerializer.string());
        return redisTemplate;
    }

    @Bean
    ZSetOperations<String, String> shipperZSetOperations(
            RedisTemplate<String, String> shipperRedisTemplate) {
        return shipperRedisTemplate.opsForZSet();
    }
}

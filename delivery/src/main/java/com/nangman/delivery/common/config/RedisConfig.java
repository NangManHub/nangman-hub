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
    public RedisTemplate<String, UUID> shipperRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        //TODO: UUID -> ShipperDto 로 변경
        RedisTemplate<String, UUID> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // TODO: JAVA로 변경
        redisTemplate.setValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }

    @Bean
    ZSetOperations<String, UUID> shipperZSetOperations(
            RedisTemplate<String, UUID> shipperRedisTemplate) {
        return shipperRedisTemplate.opsForZSet();
    }
}

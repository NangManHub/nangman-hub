package com.nangman.delivery.common.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerURL;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerURL);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


    // Kafka 컨슈머 팩토리를 생성하는 빈을 정의합니다.
    // ConsumerFactory는 Kafka 컨슈머 인스턴스를 생성하는 데 사용됩니다.
    // 각 컨슈머는 이 팩토리를 통해 생성된 설정을 기반으로 작동합니다.
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // 컨슈머 팩토리 설정을 위한 맵을 생성합니다.
        Map<String, Object> configProps = new HashMap<>();
        // Kafka 브로커의 주소를 설정합니다.
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerURL);
        // 메시지 키의 디시리얼라이저 클래스를 설정합니다.
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        // 메시지 값의 디시리얼라이저 클래스를 설정합니다.
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // 설정된 프로퍼티로 DefaultKafkaConsumerFactory를 생성하여 반환합니다.
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    // Kafka 리스너 컨테이너 팩토리를 생성하는 빈을 정의합니다.
    // ConcurrentKafkaListenerContainerFactory는 Kafka 메시지를 비동기적으로 수신하는 리스너 컨테이너를 생성하는 데 사용됩니다.
    // 이 팩토리는 @KafkaListener 어노테이션이 붙은 메서드들을 실행할 컨테이너를 제공합니다.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        // ConcurrentKafkaListenerContainerFactory를 생성합니다.
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 컨슈머 팩토리를 리스너 컨테이너 팩토리에 설정합니다.
        factory.setConsumerFactory(consumerFactory());
        // 설정된 리스너 컨테이너 팩토리를 반환합니다.
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
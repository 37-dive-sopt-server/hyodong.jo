package org.sopt.global.config.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final CacheTtlProperties cacheTtlProperties;

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class)
                .build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.EVERYTHING);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer(ObjectMapper redisObjectMapper) {
        // JSON 형식으로 직렬화
        return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisSerializer<Object> redisSerializer) {

        return RedisCacheConfiguration.defaultCacheConfig()
                // key -> String으로 저장
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer())
                )
                // value -> JSON으로 저장
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(redisSerializer)
                )
                // null값 -> 캐싱x
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(cacheTtlProperties.defaultTtl()));
    }

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory,
            RedisCacheConfiguration redisCacheConfiguration){

        // 캐시별 설정을 담는 Map
        Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // articleDetail 캐시
        cacheConfigurations.put("articleDetail", redisCacheConfiguration.entryTtl(Duration.ofMillis(cacheTtlProperties.articleDetail())));

        cacheConfigurations.put("articleList",redisCacheConfiguration.entryTtl(Duration.ofMillis(cacheTtlProperties.articleList())));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}

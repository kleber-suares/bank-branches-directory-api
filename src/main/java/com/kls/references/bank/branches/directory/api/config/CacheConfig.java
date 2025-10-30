package com.kls.references.bank.branches.directory.api.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    public static final String BRANCHES_DISTANCES_LIST_CACHE = "branchesDistances";

    private final CacheTtlConfig redisCacheManagerConfig;

    public CacheConfig(final CacheTtlConfig cacheTtlConfigValue) {
        this.redisCacheManagerConfig = cacheTtlConfigValue;
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager cacheManager(final RedisConnectionFactory connectionFactory) {
        log.info("Initializing Redis Cache manager...");

        long ttlCacheInMinutes = redisCacheManagerConfig.getBranchesDistancesCacheTtlInMin();

        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer(
            new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        );

        RedisCacheConfiguration branchesDistancesCacheConfig =
            RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                    SerializationPair.fromSerializer(serializer)
                )
                .entryTtl(Duration.ofMinutes(ttlCacheInMinutes));

        Map<String, RedisCacheConfiguration> cacheConfigurationsMap = new HashMap<>();
        cacheConfigurationsMap.put(BRANCHES_DISTANCES_LIST_CACHE, branchesDistancesCacheConfig);

        log.info("Setting cache {} time to live to {} mins", BRANCHES_DISTANCES_LIST_CACHE, ttlCacheInMinutes);

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory)
            .withInitialCacheConfigurations(cacheConfigurationsMap)
            .build();
    }

}

package com.dat.cnpm_btl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Cấu hình cho Redis Repositories
 * Chỉ quét các repository trong package redis
 */
@Configuration
@EnableRedisRepositories(
    basePackages = "com.dat.cnpm_btl.redis"
)
public class RedisRepositoryConfig {
}

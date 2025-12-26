package org.sopt.global.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache.ttl")
public record CacheTtlProperties(

        long defaultTtl,

        long articleDetail,

        long articleList
) {
}
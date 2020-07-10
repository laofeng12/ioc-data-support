package com.ioc.datasupport.conf;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.ioc.datasupport.util.MD5Util;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * @author: lsw
 * @Date: 2020/6/22 10:46
 */
@Configuration
public class CacheConfig {

    @Primary
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCache() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(120, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(50)
                // 缓存的最大条数
                .maximumSize(1000));

        return cacheManager;
    }

    @Bean(name = "keyGenerator")
    public KeyGenerator keyGenerator() {
        return (o, method, params) -> {
            StringBuilder sb = new StringBuilder();
            for (Object obj : params) {
                sb.append(obj.toString());
            }

            return MD5Util.getMD5(sb.toString(), false, 32);
        };
    }
}

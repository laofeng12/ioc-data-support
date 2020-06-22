package com.ioc.datasupport.component;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author: lsw
 * @Date: 2020/6/10 10:43
 */
@Component
public class CaffeineCacheComponent implements ServletContextAware, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(CaffeineCacheComponent.class);

    /** 同步缓存 */
    private Cache<String, Object> cache;

    /** 异步缓存 */
    private AsyncLoadingCache<String, Object> asyncCache;

    /**
     * 初始化方法
     */
    public void init() {
        // 同步缓存
        cache = Caffeine.newBuilder()
                // 设置过期时间，缓存失效时间（默认应该是放在内存的）
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(1_000)
                .build();

        // 异步缓存
        /*asyncCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .buildAsync();*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //...
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.init();
    }

    /**
     * 设置缓存
     *
     * @param key   key
     * @param value value
     */
    public void setCacheVal(String key, Object value) {
        this.cache.put(key, value);
    }

    /**
     * 获取缓存（如果存在，否则返回null）
     *
     * @param key   key
     * @param clazz 类型
     * @return T
     */
    public <T> T getCacheVal(String key, Class<T> clazz) {
        return (T)this.cache.getIfPresent(key);
    }

    /**
     * 删除缓存值
     *
     * @param key key
     */
    public void delCacheVal(String key) {
        this.cache.invalidate(key);
    }

    /**
     * 获取值传入函数（同步）
     *
     * @param key key
     * @param mappingFunction 函数
     * @param <T> T
     * @return 缓存值
     */
    public <T> T getVal(String key, Function<? super String, ? extends T> mappingFunction) {
        return (T) this.cache.get(key, mappingFunction);
    }

    /**
     * 异步加载
     *
     * @param key key
     */
    public void asynGet(String key) {


    }
}

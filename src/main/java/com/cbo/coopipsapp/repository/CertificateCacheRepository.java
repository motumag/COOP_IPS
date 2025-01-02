package com.cbo.coopipsapp.repository;

import com.cbo.coopipsapp.exceptions.CertificateCacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link )
 */
@Repository
public class CertificateCacheRepository implements CacheRepository {

    private long ttl;
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public CertificateCacheRepository(StringRedisTemplate redisTemplate,
                                      @Value("${spring.cache.redis.time-to-live}") long ttl) {
        this.redisTemplate = redisTemplate;
        valueOps = redisTemplate.opsForValue();
        this.ttl = ttl;
    }
//("${spring.redis.timeToLive}
//    spring.cache.redis.time-to-live

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */
    @Override
    public void put(String key, String value) {
        try {
            valueOps.set(key, String.valueOf(value));
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        } catch (RuntimeException e) {
            throw new CertificateCacheException("Error while saving to cache ", e);
        }
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return cached value
     */
    @Override
    public Optional<String> get(String key) {
        try {
            Boolean b = redisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(b)) {
                return Optional.ofNullable(valueOps.get(key));
            } else {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            throw new CertificateCacheException("Error while retrieving from the cache ", e);
        }
    }

    /**
     * Remove the cached value
     *
     * @param key cached key
     */
    @Override
    public void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (RuntimeException e) {
            throw new CertificateCacheException("Error while removing from the cache ", e);
        }
    }

}

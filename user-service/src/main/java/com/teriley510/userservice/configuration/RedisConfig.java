package com.teriley510.userservice.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Objects;
@Configuration
@EnableRedisRepositories
@EnableAutoConfiguration
public class RedisConfig {

    private RedissonClient redissonClient;

    public RedissonClient getClient() {
        if(Objects.isNull(this.redissonClient)) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379");
            redissonClient = Redisson.create(config);
        }
        return redissonClient;
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient() {
        return  getClient().reactive();
    }
}

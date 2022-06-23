package com.company.bookStore.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookCacheConfig {

    @Bean
    public Config cacheConfig(){
        return new Config().setInstanceName("hazel-instance")
                .addMapConfig(new MapConfig()
                        .setName("book-cache")
                        .setTimeToLiveSeconds(3000));
    }
}

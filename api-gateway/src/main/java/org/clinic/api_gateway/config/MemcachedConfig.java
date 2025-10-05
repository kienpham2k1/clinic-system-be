package org.clinic.api_gateway.config;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemcachedConfig {

    @Value("${memcached.server:127.0.0.1}")
    private String server;
    @Value("${memcached.port:11211}")
    private String port;

    @Bean
    public MemcachedClient memcachedClient() throws Exception {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(server + ":" + port);
        return builder.build();
    }
}

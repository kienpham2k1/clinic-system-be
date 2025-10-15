package org.clinic.api_gateway.controller;

import lombok.AllArgsConstructor;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/test-memcached")
@AllArgsConstructor
public class TestMemcachedController {
    private final MemcachedClient memcachedClient;

    @GetMapping
    public String testMemcached() throws InterruptedException, TimeoutException, MemcachedException {
        String result = memcachedClient.get("test-memcached");
        final String rs = "Test memcached result here";
        if (result != null) {
            return result;
        }
        memcachedClient.set("test-memcached", 300, rs);
        return "Memcached new load here: " + rs;
    }
}

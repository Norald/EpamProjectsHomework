package com.pigorv.springcloud.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users")
public interface ClientOrder {
    @GetMapping(value = "/{userName}")
    ResponseEntity<Object> getUser(@PathVariable String userName);
}

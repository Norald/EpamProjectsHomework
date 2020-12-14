package com.pigorv.springcloud.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "products")
public interface ProductOrder {
    @PutMapping(value = "/{productName}")
    Object removeOneProduct(@PathVariable String productName);
}

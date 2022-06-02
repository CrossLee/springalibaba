package com.qdstorm.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "stock-nacos", path = "/stock", fallback = StockFeignServiceFallback.class)
public interface StockFeignService {
    @RequestMapping("/reduct2")
    public String reduct2();
}

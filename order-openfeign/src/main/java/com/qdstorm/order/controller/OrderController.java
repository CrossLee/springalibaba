package com.qdstorm.order.controller;

import com.qdstorm.order.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.System.out;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    StockFeignService stockFeignService;

    @RequestMapping("/add")
    public String add() {
        out.println("下单成功");
        String msg = stockFeignService.reduct();
        return "Hello Openfeign" + msg;
    }
}

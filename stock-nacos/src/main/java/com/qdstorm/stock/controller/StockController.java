package com.qdstorm.stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.out;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct() {
        out.println("扣减库存");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "扣减库存" + port;
    }

    @RequestMapping("/reduct2")
    public String reduct2() {
        out.println("扣减库存");
        int i = 1 / 0;
        return "扣减库存" + port;
    }
}

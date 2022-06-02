package com.qdstorm.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qdstorm.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @RequestMapping("/add")
    public String add() {
        out.println("下单成功");
        return "生成订单";
    }

    @RequestMapping("/get")
    public String get() {
        return "查询订单";
    }

    @RequestMapping(value = "/flow")
//    @SentinelResource(value = "flow", blockHandler = "flowBlockerHandler")
    public String flow(){
        return "正常访问 flow";
    }

    @RequestMapping(value = "/flowThread")
//    @SentinelResource(value = "flowThread", blockHandler = "flowBlockerHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "正常访问 flowThread";
    }

    public String flowBlockerHandler(BlockException e){
        e.printStackTrace();
        return "流控";
    }

    @RequestMapping("/test1")
    public String test1(){
        return orderService.getUser();
    }

    @RequestMapping("/test2")
    public String test2(){
        return orderService.getUser();
    }

    @RequestMapping("err")
    public String err(){
        int i = 1 /0;
        return "hello";
    }

    @RequestMapping("/get/{id}")
    @SentinelResource(value = "getById", blockHandler = "hotBlockHandler")
    public String getById(@PathVariable("id") Integer id){
        out.println("正常访问");
        return "正常访问";
    }

    public String hotBlockHandler(@PathVariable("id") Integer id, BlockException e) {
        return "热点异常处理";
    }
}

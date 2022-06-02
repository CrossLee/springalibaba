package com.qdstorm.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService{
    @Override
    @SentinelResource(value = "getUser", blockHandler = "getUserHandler")
    public String getUser() {
        return "查询用户";
    }

    public String getUserHandler(BlockException e) {
        e.printStackTrace();
        return "查询限流了";
    }
}

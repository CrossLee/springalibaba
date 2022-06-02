package com.qdstorm.sentinelnew.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.qdstorm.sentinelnew.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    @RequestMapping(value = "/hello")
    public String hello() {

        Entry entry = null;
        try {
            entry = SphU.entry(RESOURCE_NAME);
            String str = "hello world";
            log.info("=======" + str + "=======");
            return str;
        } catch (BlockException e) {
            e.printStackTrace();
            log.info("block");
            return "被流控了";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    @RequestMapping(value = "/user")
    @SentinelResource(value = USER_RESOURCE_NAME,
            blockHandler = "blockHandlerForGetUser",
//            blockHandlerClass = User.class,
            fallback = "fallbackHandlerForGetUser"
//            fallbackClass = User.class
//            exceptionsToIgnore = {ArithmeticException.class}
    )
    public User getUser(String id) {
        int a = 1 / 0;
        return new User("cross");
    }

    @RequestMapping(value = "/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME,
            blockHandler = "degradeHandler"
    )
    public String degrade() {
        int a = 1 / 0;
        return "degrade";
    }

    public User blockHandlerForGetUser(String id, BlockException e) {
        e.printStackTrace();
        return new User("被流控了");
    }

    public User fallbackHandlerForGetUser(String id, Throwable e) {
        e.printStackTrace();
        return new User("异常处理");
    }

    public String degradeHandler(BlockException e){
        e.printStackTrace();
        return "降级了";
    }

    /**
     * 在 Bean 初始化后执行
     */
    @PostConstruct
    private static void initFlowRules() {
        // 限流规则（通过硬编码的方式）
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RESOURCE_NAME);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);
        rules.add(flowRule);
//        FlowRuleManager.loadRules(rules);

        // 限流规则（通过注解方式）
        FlowRule flowRule1 = new FlowRule();
        flowRule1.setResource(USER_RESOURCE_NAME);
        flowRule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule1.setCount(1);
        rules.add(flowRule1);
        FlowRuleManager.loadRules(rules);

        // 降级规则（通过注解方式）
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setCount(2);
        degradeRule.setMinRequestAmount(2);
        degradeRule.setStatIntervalMs(60*1000);
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }

}

package com.demo.order.impl;

import com.demo.order.api.IOrderService;
import com.demo.order.feign.IMemberServiceFeign;
import com.framework.commons.core.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:14:15
 */
@Slf4j
@RefreshScope // 将所有需要用到的配置写到一个类里面再加上该注解，这样不用每个地方都写这个注解
@RestController
public class OrderServiceImpl implements IOrderService {
    @Value("${server.port}")
    private String port;
    @Value("${nameInfo}")
    private String nameInfo;
    @Value("${namex}")
    private String namex;
    @Autowired
    private IMemberServiceFeign iMemberServiceFeign;

    @Override
    @RequestMapping("/getOrder")
    public Result getOrder(String name) {
        log.info("getOrder线程名称：{}", Thread.currentThread().getName());
        Result result = this.iMemberServiceFeign.getMember(name);
        return Result.success("订单" + port + "      " + name + "     会员" + result.getData());
    }

    /**
     * HystrixCommand 默认开启服务隔离（以线程池方式），默认开启服务降级（执行getOrderInfoFallback方法），默认开启熔断机制
     */
    @Override
    @HystrixCommand(fallbackMethod = "getOrderInfoFallback")
    @RequestMapping("/getOrderInfo")
    public Result getOrderInfo(String name) {
        log.warn("getOrderInfo线程名称{}", Thread.currentThread().getName());
        Result result = this.iMemberServiceFeign.getMemberInfo("getMemberInfo");
        return result;
    }

    public Result getOrderInfoFallback(String name) {
        log.error("{}", "走降级方法getOrderInfoFallback");
        return Result.error("getOrderInfo被熔断了");
    }

    @Override
    @RequestMapping("/getOrderInfo2")
    public Result getOrderInfo2(String name) {
        log.warn("getOrderInfo2{}", Thread.currentThread().getName());
        Result result = this.iMemberServiceFeign.getMemberInfo("getMemberInfo");
        return result;
    }

    @Override
    @RequestMapping("/getSimple")
    public Result getSimple() {
        log.error("getSimple线程名称{}", Thread.currentThread().getName());
        return Result.success(namex+"       "+nameInfo);
    }
}

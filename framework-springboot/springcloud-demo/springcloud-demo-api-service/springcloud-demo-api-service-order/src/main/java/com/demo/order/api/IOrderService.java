package com.demo.order.api;

import com.framework.commons.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:14:32
 */
public interface IOrderService {
    @RequestMapping("/getOrder")
    Result getOrder(@RequestParam String name);

    @RequestMapping("/getOrderInfo")
    Result getOrderInfo(@RequestParam String name);

    @RequestMapping("/getOrderInfo2")
    Result getOrderInfo2(@RequestParam String name);

    @RequestMapping("/getSimple")
    Result getSimple();
}

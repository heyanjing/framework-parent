package com.demo.member.impl;

import com.demo.member.api.IMemberService;
import com.framework.commons.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:14:15
 */
@Slf4j
@RestController
public class MemberServiceImpl implements IMemberService {
    @Value("${server.port}")
    private String port;

    @Override
    @RequestMapping("/getMember")
    public Result getMember(String name) {
        return Result.success("会员" + port + "      " + name);
    }

    @Override
    @RequestMapping("/getMemberInfo")
    public Result getMemberInfo(String name) {
        try {
            log.error("getMemberInfo线程名称{}", Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success("getMemberInfo超时请求成功");
    }
}

package com.demo.member.api;

import com.framework.commons.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:14:03
 */
public interface IMemberService {
    @RequestMapping("/getMember")
    Result getMember(@RequestParam  String name);
    @RequestMapping("/getMemberInfo")
    Result getMemberInfo(@RequestParam  String name);
}

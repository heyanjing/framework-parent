package com.demo.order.feign;

import com.demo.member.api.IMemberService;
import com.demo.order.fallback.MemberServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:14:47
 */
@FeignClient(value = "member-service", fallback = MemberServiceFeignFallback.class)
public interface IMemberServiceFeign extends IMemberService {
}

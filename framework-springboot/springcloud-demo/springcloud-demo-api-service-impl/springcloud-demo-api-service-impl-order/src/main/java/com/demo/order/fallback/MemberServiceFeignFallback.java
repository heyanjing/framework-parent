package com.demo.order.fallback;

import com.demo.order.feign.IMemberServiceFeign;
import com.framework.commons.core.Result;
import org.springframework.stereotype.Component;

/**
 * @author heyanjing
 * date:2018-12-19 2018/12/19:21:02
 */
@Component
public class MemberServiceFeignFallback implements IMemberServiceFeign {
    @Override
    public Result getMember(String name) {
        return Result.error("类种的getMember被熔断了");
    }

    @Override
    public Result getMemberInfo(String name) {
        return Result.error("类种的getMemberInfo被熔断了");
    }
}

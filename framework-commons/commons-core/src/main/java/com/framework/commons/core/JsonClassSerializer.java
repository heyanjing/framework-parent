package com.framework.commons.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 所有含有多态类的序列化、反序列化均需实现此接口
 *
 * @author heyanjing
 * date:2018-12-09 2018/12/9:16:33
 */
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
public interface JsonClassSerializer {
}

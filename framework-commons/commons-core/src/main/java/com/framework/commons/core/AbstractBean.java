package com.framework.commons.core;

import java.io.Serializable;

/**
 * 所有展示层与服务层之间的数据传输对象均实现该接口
 *
 * @author heyanjing
 * date:2018-12-09 2018/12/9:16:24
 */
public abstract class AbstractBean implements /*JsonClassSerializer,*/ Serializable {
    protected static long serialVersionUID = 1L;

    /**
     * 拷贝自身的属性
     */
    public <T> T copyTo(Class<T> to) throws Exception {
        T obj = ObjectCopy.copyTo(this, to);
        return obj;
    }
}

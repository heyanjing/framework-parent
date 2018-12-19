package com.framework.commons.core;

import java.util.List;
import java.util.Map;

/**
 * @author heyanjing
 * date:2018-12-11 2018/12/11:18:16
 */
public class BaseCopy {
    public <T> T copyTo(Object obj, Class<T> toObj) throws Exception {
        if (obj == null) {
            return null;
        }
        return ObjectCopy.copyTo(obj, toObj);
    }

    public <T> List<T> copyTo(List from, Class<T> to) {
        if (from == null) {
            return null;
        }
        return ObjectCopy.copyListTo(from, to, true);
    }

    public <T> T copyMapTo(Map map, Class<T> toObj) throws Exception {
        if (map == null) {
            return null;
        }
        return ObjectCopy.copyMapTo(map, toObj);
    }
}

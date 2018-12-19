package com.framework.commons.core;

import com.framework.commons.core.annotation.FieldMapped;
import com.framework.commons.core.annotation.desensitized.Desensitized;
import com.framework.commons.core.util.DateUtil;
import com.framework.commons.core.util.DesensitizedUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对象值复制：
 * ——注：支持字段属性名相同的值复制。当属性名不相同时，可使用 属性注解@FieldMapped映射
 *
 * @author heyanjing
 * date:2018-12-09 2018/12/9:16:55
 */
@Slf4j
public class ObjectCopy {
    /**
     * 获取来源字段。并处理@FieldMapped 的字段映射（如属性名不同时）
     *
     * @param sourCls 有属性值的bean
     * @param toField 要被赋值的字段
     * @return toField在sourCls中存在则返回sourCls中对应的字段，不存返回null
     */
    private static Field getField(Class sourCls, Field toField) {
        Set<String> toNames = new HashSet<>();
        toNames.add(toField.getName());
        //目标字段注解
        FieldMapped fMapped = toField.getAnnotation(FieldMapped.class);
        if (fMapped != null) {
            for (String currentName : fMapped.names()) {
                if (!toNames.contains(currentName)) {
                    toNames.add(currentName);
                }
            }
        }

        do {
            for (Field from : sourCls.getDeclaredFields()) {
                //ru
                if (toNames.contains(from.getName())) {
                    return from;
                }
                //来源字段注解
                fMapped = from.getAnnotation(FieldMapped.class);
                if (fMapped != null) {
                    for (String currentName : fMapped.names()) {
                        if (toNames.contains(currentName)) {
                            return from;
                        }
                    }
                }
            }
            sourCls = sourCls.getSuperclass();
        } while (sourCls != Object.class);

        return null;
    }

    /**
     * 脱敏操作（按照规则转化需要脱敏的字段并设置新值）
     * 目前只支持String类型的字段，如需要其他类型如BigDecimal、Date等类型，可以添加
     *
     * @param javaBean
     * @param field
     * @param value
     * @throws IllegalAccessException
     */
    private static void setDesensitizedValueForField(Object javaBean, Field field, Object value) throws IllegalAccessException {
        //处理自身的属性
        Desensitized annotation = field.getAnnotation(Desensitized.class);
        if (field.getType().equals(String.class) && null != annotation) {
            String valueStr = (String) value;
            if (StringUtils.isNotBlank(valueStr)) {
                switch (annotation.type()) {
                    case CHINESE_NAME: {
                        field.set(javaBean, DesensitizedUtils.chineseName(valueStr));
                        break;
                    }
                    case ID_CARD: {
                        field.set(javaBean, DesensitizedUtils.idCardNum(valueStr));
                        break;
                    }
                    case FIXED_PHONE: {
                        field.set(javaBean, DesensitizedUtils.fixedPhone(valueStr));
                        break;
                    }
                    case MOBILE_PHONE: {
                        field.set(javaBean, DesensitizedUtils.mobilePhone(valueStr));
                        break;
                    }
                    case ADDRESS: {
                        field.set(javaBean, DesensitizedUtils.address(valueStr, 8));
                        break;
                    }
                    case EMAIL: {
                        field.set(javaBean, DesensitizedUtils.email(valueStr));
                        break;
                    }
                    case BANK_CARD: {
                        field.set(javaBean, DesensitizedUtils.bankCard(valueStr));
                        break;
                    }
                    case PASSWORD: {
                        field.set(javaBean, DesensitizedUtils.password(valueStr));
                        break;
                    }
                    default: {

                    }
                }
            }
        }
    }

    /**
     * 对象属性值复制。
     *
     * @param obj   值来源对象
     * @param toObj 值目标对象class
     */
    public static <T> T copyTo(Object obj, Class<T> toObj) throws Exception {
        return copyTo(obj, toObj, false);
    }

    /**
     * 对象属性值复制。
     *
     * @param obj           值来源对象
     * @param toObj         值目标对象class
     * @param isDesensitize 是否处理敏感信息
     */
    public static <T> T copyTo(Object obj, Class<T> toObj, boolean isDesensitize) throws Exception {
        //创建目标对象实例
        T toObjIns = toObj.newInstance();
        //遍历目标所有属性
        Class toCls = toObj;
        Class sourCls;
        Field fromField;
        String fromType;
        //遍历源属性
        do {
            //源属性集
            Field[] toFields = toCls.getDeclaredFields();
            for (Field toField : toFields) {
                toField.setAccessible(true);
                String name = toField.getName();
                //得到此属性的类型
                String type = toField.getType().getTypeName();
                sourCls = obj.getClass();
                fromField = getField(sourCls, toField);
                if (fromField == null) {
                    continue;
                }
                fromField.setAccessible(true);
                Object fromVal = fromField.get(obj);
                if (fromVal == null || StringUtils.isBlank(fromVal.toString())) {
                    continue;
                }

                fromType = fromField.getType().getTypeName();
                log.debug("{}", "拷贝属性值");
                if (type.equals(fromType)) {
                    if (toField.getAnnotation(Desensitized.class) != null && isDesensitize) {
                        //复制敏感属性的值
                        setDesensitizedValueForField(toObjIns, toField, fromField.get(obj));
                    } else {
                        toField.set(toObjIns, fromField.get(obj));
                    }
                } else {
                    //类型转换
                    try {
                        if (type.endsWith("String")) {
                            String value = String.valueOf(fromField.get(obj));
                            if (toField.getAnnotation(Desensitized.class) != null && isDesensitize) {
                                setDesensitizedValueForField(toObjIns, toField, value);
                            } else {
                                toField.set(toObjIns, value);
                            }
                        } else if (type.endsWith("int") || type.endsWith("Integer")) {
                            if (fromType.endsWith("int") || fromType.endsWith("Integer")) {
                                toField.set(toObjIns, fromField.get(obj));
                            } else {
                                toField.set(toObjIns, Integer.valueOf(String.valueOf(fromField.get(obj))));
                            }
                        } else if (type.endsWith("Date")) {
                            if (fromType.endsWith("Date")) {
                                toField.set(toObjIns, fromField.get(obj));
                            } else {
                                long d;
                                if (fromType.endsWith("int") || fromType.endsWith("Integer") || type.endsWith("long") || type.endsWith("Long")) {
                                    d = (Long) fromField.get(obj);
                                } else {
                                    d = Long.valueOf(String.valueOf(fromField.get(obj)));
                                }
                                if (d == 0) {
                                    throw new Exception();
                                }
                                toField.set(toObjIns, new Date(d));
                            }
                        } else if (type.endsWith("long") || type.endsWith("Long")) {
                            if (fromType.endsWith("long") || fromType.endsWith("Long")) {
                                toField.set(toObjIns, fromField.get(obj));
                            } else {
                                toField.set(toObjIns, Long.valueOf(String.valueOf(fromField.get(obj))));
                            }
                        } else if (type.endsWith("short") || type.endsWith("Short")) {
                            if (fromType.endsWith("short") || fromType.endsWith("Short")) {
                                toField.set(toObjIns, fromField.get(obj));
                            } else {
                                toField.set(toObjIns, Short.valueOf(String.valueOf(fromField.get(obj))));
                            }
                        } else {
                            throw new Exception("类型转换失败！");
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("类型转换失败:" + obj.getClass().getName() + "." + name + "[" + fromType + "] convert to " + toObj.getName() + "." + name + "[" + type + "]");
                    }
                }
            }

            toCls = toCls.getSuperclass();
        } while (toCls != Object.class);
        return toObjIns;
    }

    /**
     * 对象复制：一个List列表
     *
     * @param from          来源列表
     * @param to            目标对象类
     * @param isDesensitize
     * @param <T>
     * @return 目标对象列表
     */
    public static <T> List<T> copyListTo(List from, Class<T> to, boolean isDesensitize) {
        List<T> toList = new ArrayList<>();
        for (Object cur : from) {
            try {
                toList.add(copyTo(cur, to, isDesensitize));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        return toList;
    }

    public static <T> List<T> copyListTo(List from, Class<T> to) {
        return copyListTo(from, to, false);
    }

    /**
     * 对象属性值复制
     *
     * @param map   来源MAap
     * @param toObj 目标对象类
     * @return 目标对象列表
     */
    public static <T> T copyMapTo(Map map, Class<T> toObj) throws Exception {
        return copyMapTo(map, toObj, false);
    }

    /**
     * 对象属性值复制
     *
     * @param map   来源MAap
     * @param toObj 目标对象类
     * @return 目标对象列表
     */
    public static <T> T copyMapTo(Map map, Class<T> toObj, boolean isDesensitize) throws Exception {
        //创建目标对象实例
        T toObjIns = toObj.newInstance();
        //遍历目标所有属性
        Class toCls = toObj;
        //遍历源属性
        do {
            //源属性集
            Field[] toFlds = toCls.getDeclaredFields();
            for (Field currentField : toFlds) {
                currentField.setAccessible(true);
                String mapKey = currentField.getName();
                Object mapVal = map.get(mapKey);
                if (mapVal == null) {
                    continue;
                }
                //得到此属性的类型
                String type = currentField.getType().getTypeName();
                //类型转换
                try {
                    log.debug("map中的值:{}", mapVal);
                    if (type.endsWith("String")) {
                        if (currentField.getAnnotation(Desensitized.class) != null && isDesensitize) {
                            setDesensitizedValueForField(toObjIns, currentField, mapVal);
                        } else {
                            currentField.set(toObjIns, String.valueOf(mapVal));
                        }
                    } else if (type.endsWith("int") || type.endsWith("Integer")) {
                        currentField.set(toObjIns, Integer.valueOf(String.valueOf(mapVal)));
                    } else if (type.endsWith("Date")) {
                        if (mapVal instanceof Timestamp) {
                            currentField.set(toObjIns, DateUtil.parseDateTime("" + mapVal));
                        } else {
                            currentField.set(toObjIns, DateUtil.parseDate("" + mapVal));
                        }
                    } else if (type.endsWith("long") || type.endsWith("Long")) {
                        currentField.set(toObjIns, Long.valueOf(String.valueOf(mapVal)));
                    } else if (type.endsWith("short") || type.endsWith("Short")) {
                        currentField.set(toObjIns, Short.valueOf(String.valueOf(mapVal)));
                    } else {
                        throw new Exception("类型转换失败！");
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("类型转换失败:" + "map" + "." + mapKey + "[" + mapVal + "] convert to " + toObj.getName() + "." + mapKey + "[" + type + "]");
                }
            }
            toCls = toCls.getSuperclass();
        } while (toCls != Object.class);
        return toObjIns;
    }
}

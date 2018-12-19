package com.framework.commons.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean工具类
 */
@Slf4j
public final class BeanUtil {

    /**
     * 转换为数字
     *
     * @param value 对象
     * @return 数字
     */
    private static BigDecimal toNumber(Object value) {
        if (value == null) {
            return new BigDecimal(0);
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        } else if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        throw new RuntimeException("[" + value.getClass() + ";value=" + value + "]无效的数据类型...");
    }

    /**
     * 转换为ListObject
     *
     * @param list
     * @param clazz
     * @return
     */
    public static <T> List<T> toListObject(List<Map<String, Object>> list, Class<T> clazz) {
        List<T> objectList = new ArrayList<T>(list.size());
        for (Map<String, Object> map : list) {
            try {
                T instance = clazz.newInstance();
                BeanUtil.copy(map, instance);
                objectList.add(instance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objectList;
    }

    /**
     * 数据拷贝
     *
     * @param src  源
     * @param dest 目标
     */
    public static void copy(Object src, Object dest) {
        if (src == null || dest == null) {
            return;
        }
        if (src instanceof Map) {
            copyMap2Bean(null, (Map<?, ?>) src, dest);
        } else {
            copyBean2Bean(null, src, dest);
        }
    }

    /**
     * 设置字段值
     *
     * @param dest
     * @param field
     * @param value
     */
    public static void setFieldValue(Object dest, Field field, Object value) throws Exception {
        field.setAccessible(true);
        if (value == null) {
            field.set(dest, null);
        } else if (field.getType().isAssignableFrom(value.getClass())) {
            field.set(dest, value);
        } else {
            String s = String.valueOf(value);
            Class<?> clazz = field.getType();
            if (Long.class.isAssignableFrom(clazz)) {
                field.set(dest, Long.valueOf(s));
            }
            if (BigDecimal.class.isAssignableFrom(clazz)) {
                field.set(dest, new BigDecimal(s));
            } else if (java.sql.Date.class.isAssignableFrom(clazz)) {// java.sql.Date
                Timestamp date = DateUtil.parse(s);
                field.set(dest, new java.sql.Date(date.getTime()));
            } else if (Date.class.isAssignableFrom(clazz)) {
                Timestamp date = DateUtil.parse(s);
                field.set(dest, date);
            } else if (String.class.isAssignableFrom(clazz)) {
                field.set(dest, s);
            } else {
                Constructor<?> c = clazz.getConstructor(new Class[]{String.class});
                Object object = c.newInstance(s);
                field.set(dest, object);
            }
        }
    }

    /**
     * 设置字典值
     *
     * @param obj       目标对象
     * @param fieldName 字段名
     * @param value     字段值
     * @throws Exception
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null) {
            return;
        }
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    field.set(obj, value);
                }
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    /**
     * 取得字段值
     *
     * @param obj       目标对象
     * @param fieldName 字段名
     * @return
     */
    @SuppressWarnings("all")
    public static <T> T getFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        Object value = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    value = field.get(obj);
                    break;
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return (T) value;
    }

    /**
     * 取得字段值
     *
     * @param obj   目标对象
     * @param field 字段名
     * @return
     */
    @SuppressWarnings("all")
    public static <T> T getFieldValue(Object obj, Field field) {
        if (obj == null) {
            return null;
        }
        Object value = null;
        try {
            return (T) field.get(obj);
        } catch (Exception e) {
            //do nothing
        }
        return (T) value;
    }

    /**
     * 数据拷贝
     *
     * @param prefix 源前缀
     * @param src    源
     * @param dest   目标
     */
    public static void copy(String prefix, Object src, Object dest) {
        if (src == null || dest == null) {
            return;
        }
        if (src instanceof Map) {
            copyMap2Bean(prefix, (Map<?, ?>) src, dest);
        } else {
            copyBean2Bean(prefix, src, dest);
        }
    }

    /**
     * 判断对象的属性值是否都是为空
     *
     * @param obj
     * @return
     */
    public static boolean objectPropertyIsCreate(Object obj) {
        Field[] srcFields = obj.getClass().getDeclaredFields();
        boolean flag = false;
        for (Field field : srcFields) {
            try {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.get(obj) != null && !"".equals(field.get(obj)) && !" ".equals(field.get(obj))
                            && !"null".equals(field.get(obj))) {
                        flag = true;
                        return flag;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return flag;
    }

    /**
     * 拷贝Bean->Bean
     *
     * @param src  源
     * @param dest 目标
     */
    private static void copyBean2Bean(String prefix, Object src, Object dest) {
        Class<?> srcClass = src.getClass();
        Class<?> destClass = dest.getClass();
        try {
            if (srcClass.equals(destClass)) {// 源类型与目标类型相同
                Field[] fields = getBeanFields(src.getClass());
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                        field.setAccessible(true);
                        field.set(dest, field.get(src));
                    }
                }
            } else {// 源类型与目标类型不同
                Field[] srcFields = getBeanFields(src.getClass());
                Field[] destFields = getBeanFields(dest.getClass());
                for (Field srcField : srcFields) {
                    for (Field destField : destFields) {
                        if (!Modifier.isStatic(srcField.getModifiers()) && !Modifier.isFinal(srcField.getModifiers())) {
                            if (!Modifier.isStatic(destField.getModifiers())
                                    && !Modifier.isFinal(destField.getModifiers())) {
                                if (srcField.getName().equals(destField.getName())
                                        && destField.getType().isAssignableFrom(srcField.getType())) {
                                    srcField.setAccessible(true);
                                    destField.setAccessible(true);
                                    Object srcValue = srcField.get(src);
                                    destField.set(dest, srcValue);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 拷贝Map->Bean
     *
     * @param src  源
     * @param dest 目标
     */
    private static void copyMap2Bean(String prefix, Map<?, ?> src, Object dest) {
        Field[] fields = getBeanFields(dest.getClass());
        for (Field field : fields) {
            if (isAccessField(field)) {
                field.setAccessible(true);
                if (isSetValueEnable(prefix, src, field)) {// 是否需要设置值,Map里面有的才映射过去
                    setFieldValue(prefix, src, dest, field);
                }
            }
        }
    }

    /**
     * 是否是需要设置的字段
     *
     * @param field
     * @return
     */
    private static boolean isAccessField(Field field) {
        Class<?> clazz = field.getType();
        int m = field.getModifiers();
        if ((Modifier.isPrivate(m) || Modifier.isProtected(m)) && !Modifier.isStatic(m)) {// 是private
            // 非
            // static
            if (Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz)
                    || String.class.isAssignableFrom(clazz) || clazz.isPrimitive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得Bean字段
     *
     * @param clazz
     * @return
     */
    public static Field[] getBeanFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        while ((clazz = clazz.getSuperclass()) != null) {
            Field[] supperFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(supperFields));
        }
        List<Field> accessFields = new ArrayList<Field>();
        for (Field field : fields) {
            if (isAccessField(field)) {
                accessFields.add(field);
            }
        }
        return accessFields.toArray(new Field[]{});
    }

    /**
     * 取得Bean字段(包括复杂类型的字段)
     *
     * @param clazz
     * @return
     */
    public static Field[] getFieldsOfClass(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        while ((clazz = clazz.getSuperclass()) != null) {
            Field[] supperFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(supperFields));
        }
        List<Field> accessFields = new ArrayList<Field>();
        for (Field field : fields) {
            int m = field.getModifiers();
            if ((Modifier.isPrivate(m) || Modifier.isProtected(m)) && !Modifier.isStatic(m)) {// 是private非static
                accessFields.add(field);
            }
        }
        return accessFields.toArray(new Field[]{});
    }

    /**
     * 取得对应注解的字段
     *
     * @param clazz
     * @param annotationClass 包含该注解的字段
     * @return
     */
    public static List<Field> getAnnotationFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        while ((clazz = clazz.getSuperclass()) != null) {
            Field[] supperFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(supperFields));
        }
        List<Field> accessFields = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.getAnnotation(annotationClass) != null) {
                accessFields.add(field);
            }
        }
        return accessFields;
    }

    /**
     * 设置字段值
     *
     * @param prefix 前缀
     * @param src    源对象
     * @param dest   目标对象
     * @param field  目标字段
     */
    private static void setFieldValue(String prefix, Map<?, ?> src, Object dest, Field field) {
        Class<?> clazz = field.getType();
        String value = null;
        try {
            value = getValue(prefix, src, field);
            if (value == null) {
                if (clazz.isPrimitive()) {//基本类型
                    field.set(dest, 0);
                } else {
                    field.set(dest, null);
                }
            } else {
                boolean isNumber = NumberUtil.isNumber(value);
                if (String.class.isAssignableFrom(clazz)) {
                    field.set(dest, value);
                } else if (Integer.class.isAssignableFrom(clazz)) {
                    field.set(dest, isNumber ? Integer.valueOf(value) : null);
                } else if (Long.class.isAssignableFrom(clazz)) {
                    field.set(dest, isNumber ? Long.valueOf(value) : null);
                } else if (Double.class.isAssignableFrom(clazz)) {
                    field.set(dest, isNumber ? Double.valueOf(value) : null);
                } else if (BigDecimal.class.isAssignableFrom(clazz)) {
                    field.set(dest, isNumber ? new BigDecimal(value) : null);
                } else if (Byte.class.isAssignableFrom(clazz)) {
                    field.set(dest, isNumber ? Byte.valueOf(value) : null);
                } else if (java.sql.Date.class.isAssignableFrom(clazz)) {// java.sql.Date
                    Timestamp date = DateUtil.parse(value);
                    field.set(dest, new java.sql.Date(date.getTime()));
                } else if (Date.class.isAssignableFrom(clazz)) {
                    Timestamp date = DateUtil.parse(value);
                    field.set(dest, date);
                } else if (clazz.isPrimitive()) {//基本类型
                    if (int.class.equals(clazz)) {
                        field.set(dest, new Integer(value));
                    } else if (long.class.equals(clazz)) {
                        field.set(dest, new Long(value));
                    } else if (double.class.equals(clazz)) {
                        field.set(dest, new Double(value));
                    } else if (float.class.equals(clazz)) {
                        field.set(dest, new Float(value));
                    } else if (short.class.equals(clazz)) {
                        field.set(dest, new Short(value));
                    } else if (boolean.class.equals(clazz)) {
                        field.set(dest, new Boolean(value));
                    } else if (byte.class.equals(clazz)) {
                        field.set(dest, new Byte(value));
                    }
                } else {
                    Constructor<?> c = clazz.getConstructor(new Class[]{String.class});
                    Object object = c.newInstance(value);
                    field.set(dest, object);
                }
            }
        } catch (Exception e) {
            log.error(field + ".setValue(" + value + ")");
            e.printStackTrace();
        }
    }

    /**
     * 取得属性值
     *
     * @param src
     * @param field
     * @return
     */
    private static String getValue(String prefix, Map<?, ?> src, Field field) {
        Object value = null;
        if (StringUtils.isNotEmpty(prefix)) {
            value = src.get(merge(prefix, field.getName()));
        } else {
            value = src.get(field.getName());
        }
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Date) {
            return DateUtil.getDateTimeStr((Date) value);
        }
        return value == null ? null : value.toString();
    }

    /**
     * 字段是否需要设置值
     *
     * @param prefix
     * @param src
     * @param field
     * @return
     */
    private static boolean isSetValueEnable(String prefix, Map<?, ?> src, Field field) {
        if (StringUtils.isNotEmpty(prefix)) {
            return src.containsKey(merge(prefix, field.getName()));
        }
        return src.containsKey(field.getName());
    }

    /**
     * 取得属性
     *
     * @param prefix
     * @param name
     * @return
     */
    private static String merge(String prefix, String name) {
        return prefix + "-" + name;
    }

    /**
     * Bean转换为Map格式
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> toMap(Object bean) {
        return toMap(bean, false);
    }

    /**
     * Bean转换为Map格式
     *
     * @param bean
     * @param deepCopy 是否进行深拷贝
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object bean, boolean deepCopy) {
        if (bean == null) {
            return null;
        }
        if (Map.class.isAssignableFrom(bean.getClass())) {
            return (Map<String, Object>) bean;
        }

        Field[] fields = bean.getClass().getDeclaredFields();
        if (deepCopy) {
            fields = getFieldsOfClass(bean.getClass());
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers())) {
                    map.put(field.getName(), field.get(bean));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(bean.getClass() + " cast to map error", e);
        }
        return map;
    }

    /**
     * List<Object> 转换为Map格式
     *
     * @param list Bean对象列表
     * @return List<Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>
     */
    public static List<Map<String, Object>> toListMap(List<?> list) {
        return toListMap(list, false);
    }

    /**
     * List<Object> 转换为Map格式
     *
     * @param list Bean对象列表
     * @return List<Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>
     */
    public static List<Map<String, Object>> toListMap(List<?> list, boolean deepCopy) {
        List<Map<String, Object>> rows = new ArrayList<>(list.size());
        if (list.isEmpty()) {
            return rows;
        }
        if (Map.class.isAssignableFrom(list.get(0).getClass())) {
            return (List<Map<String, Object>>) list;
        }
        for (Object bean : list) {
            Map<String, Object> map = toMap(bean, deepCopy);
            rows.add(map);
        }
        return rows;
    }
}
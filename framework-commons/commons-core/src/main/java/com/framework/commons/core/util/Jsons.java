package com.framework.commons.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Jsons {
    public static final String DATE = "yyyy-MM-dd";

    // ########################################
    // ###***********创建ObjectMapper**************####
    // ########################################
    @SuppressWarnings("deprecation")
    public static ObjectMapper newObjectMapper(ObjectMapper mapper, String dateFormat) {
        if (mapper == null) {
            mapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            mapper.registerModule(javaTimeModule);

            //mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            //当Value 为“” 或者null 不输出）
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 单引号
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 未引号字段
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        }
        if (dateFormat == null) {
            //dateFormat = DATETIME;
            dateFormat = DATE;
        }
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        return mapper;
    }

    public static ObjectMapper getInstance(String dateFormat) {
        return newObjectMapper(null, dateFormat);
    }

    public static ObjectMapper getInstance() {
        return getInstance(null);
    }

    // ########################################
    // ###*************convert**************####
    // ########################################
    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, Class<T> clazz, TypeReference<?> type) {
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        if (type != null) {
            return mapper.convertValue(obj, type);
        } else {
            return mapper.convertValue(obj, clazz);
        }
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, Class<T> clazz) {
        return convert(mapper, obj, dateFormat, clazz, null);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, Class<T> clazz) {
        return convert(mapper, obj, null, clazz);
    }

    public static <T> T convert(Object obj, String dateFormat, Class<T> clazz) {
        return convert(null, obj, dateFormat, clazz, null);
    }

    public static <T> T convert(Object obj, Class<T> clazz) {
        return convert(obj, null, clazz);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, String dateFormat, TypeReference<?> type) {
        return convert(mapper, obj, dateFormat, null, type);
    }

    public static <T> T convert(ObjectMapper mapper, Object obj, TypeReference<?> type) {
        return convert(mapper, obj, null, type);
    }

    public static <T> T convert(Object obj, String dateFormat, TypeReference<?> type) {
        return convert(null, obj, dateFormat, null, type);
    }

    public static <T> T convert(Object obj, TypeReference<?> type) {
        return convert(obj, null, type);
    }

    // ########################################
    // ###**************toJson**************####
    // ########################################

    public static String toJson(ObjectMapper mapper, Object o, String dateFormat, boolean prettyFormat) {
        String json = null;
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        try {
            if (prettyFormat) {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                json = mapper.writeValueAsString(o);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static String toJson(ObjectMapper mapper, Object o) {
        return toJson(mapper, o, null, false);
    }

    public static String toJson(Object o) {
        return toJson(null, o, null, false);
    }

    public static String toJson(ObjectMapper mapper, Object o, boolean prettyFormat) {
        return toJson(mapper, o, null, prettyFormat);
    }

    public static String toJson(Object o, boolean prettyFormat) {
        return toJson(null, o, null, prettyFormat);
    }

    public static String toJson(Object o, String dateFormat, boolean prettyFormat) {
        return toJson(null, o, dateFormat, prettyFormat);
    }

    public static String toJson(Object o, String dateFormat) {
        return toJson(null, o, dateFormat, false);
    }

    // ########################################
    // ###**************toBean**************####
    // ########################################
    public static <T> T toBean(ObjectMapper mapper, String json, String dateFormat, Class<T> clazz, TypeReference<?> type) {
        mapper = mapper == null ? getInstance(dateFormat) : mapper;
        try {
            if (type != null) {
                return mapper.readValue(json, type);
            } else {
                return mapper.readValue(json, clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(ObjectMapper mapper, String json, Class<T> clazz) {
        return toBean(mapper, json, null, clazz, null);
    }

    public static <T> T toBean(ObjectMapper mapper, String json, TypeReference<?> type) {
        return toBean(mapper, json, null, null, type);
    }

    public static <T> T toBean(ObjectMapper mapper, String json, String dateFormat, TypeReference<?> type) {
        return toBean(mapper, json, dateFormat, null, type);
    }

    public static <T> T toBean(String json, String dateFormat, Class<T> clazz) {
        return toBean(null, json, dateFormat, clazz, null);
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        return toBean(null, json, null, clazz, null);
    }

    public static <T> T toBean(String json, TypeReference<?> type) {
        return toBean(null, json, null, null, type);
    }

    public static <T> T toBean(String json, String dateFormat, TypeReference<?> type) {
        return toBean(null, json, dateFormat, null, type);
    }

    // ########################################
    // ###*************toList**************####
    // ########################################
    public static <T> List<T> toList(ObjectMapper mapper, String json, Class<T> clazz) {
        List<T> objs = new ArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(mapper, json, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(mapper, map, clazz));
            }
        }
        return objs;
    }

    public static <T> List<T> toList(String json, String dateFormat, Class<T> clazz) {
        List<T> objs = new ArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(json, dateFormat, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(map, clazz));
            }
        }
        return objs;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        List<T> objs = new ArrayList();
        List<LinkedHashMap<String, Object>> maps = toBean(json, new TypeReference<List<Object>>() {
        });
        if (maps != null) {
            for (LinkedHashMap<String, Object> map : maps) {
                objs.add(convert(map, clazz));
            }
        }
        return objs;
    }

    public static <T> List<T> toBaseList(String json, Class<T> clazz) {
        List<T> list;
        ObjectMapper mapper = getInstance();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            list = mapper.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // ########################################
    // ###*************toMap**************####
    // ########################################
    public static Map<String, Object> toMap(ObjectMapper mapper, Object obj) {
        return convert(mapper, obj, new TypeReference<HashMap<String, Object>>() {
        });
    }

    public static Map<String, Object> toMap(Object obj) {
        return convert(obj, new TypeReference<HashMap<String, Object>>() {
        });
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        try {
            return getInstance().readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

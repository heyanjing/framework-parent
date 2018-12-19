package com.framework.commons.core.util;


/**
 * <p>系统程序用常量</p>
 *
 * @author wuxiaogang
 */
public class CommonConstant {
    /**
     * 数据库事务默认超时时间
     */
    public static final int DB_DEFAULT_TIMEOUT = 300;
    /**
     * 分页对象KEY
     */
    public static final String PAGEROW_OBJECT_KEY = "PAGEROW_OBJECT_KEY";
    /**
     * 默认画面每页的记录数
     */
    public static final int PAGEROW_DEFAULT_COUNT = 15;
    /**
     * 画面显示的页码数量
     */
    public static final int PAGEROW_CURR_NENT_COUNT = 15;


    public static final String JWT_HEADER_TOKEN_KEY = "Authorization";
    public static final String JWT_HEADER_TOKEN_PREFIX = "Symbol:";

    public static final String JWT_REDIS_TOKEN_KEY = "JWT:Authorization:";
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET_KEY = "43455454gjixiuowrmkhdiuhs#^&(klefk!";
    /**
     * jwt过期时间 毫秒
     */
    public static final long JWT_TTL = 2 * 60 * 60 * 1000;
    /**
     * jwt刷新 毫秒
     */
    public static final long JWT_TTL_REFRESH = 2 * 55 * 60 * 1000;

    public static final String FEIGN_ERROR_SYMBOL_STRING = "[Symbol/]";
}

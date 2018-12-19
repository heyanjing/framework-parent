package com.framework.commons.core.security;

import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

/**
 * DES3 加解密
 */
public class DES3 extends Crypt {
    private static Crypt crypt = new DES3();

    DES3() {
        this.type = Type.DES3;
    }

    public static String encrypt(String data) {
        return crypt.encrypt0(data);
    }

    public static String encrypt(String data, String key) {
        return crypt.encrypt0(data, key);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] data, String key) {
        return crypt.encrypt0(data, key);
    }

    public static String decrypt(String data) {
        return crypt.decrypt0(data, null);
    }

    public static String decrypt(String data, String key) {
        return crypt.decrypt0(data, key);
    }

    /**
     * 解密数据
     */
    public static byte[] decrypt(byte[] data, String key) {
        return crypt.decrypt0(data, key);
    }

    public static void main(String[] args) {
        String source = "amigoxie";
        System.out.println("原文: " + source);
        String key = "A1B2C3D4E5F60708";
        String encryptData = encrypt(source, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData, key);
        System.out.println("解密后: " + decryptData);
    }

    @Override
    protected KeySpec keyGenerator(byte[] key) throws Exception {
        byte[] curKey = key;
        int length = curKey.length;

        if (length < 24) {
            byte[] cur = new byte[24];
            System.arraycopy(curKey, 0, cur, 0, length);
            System.arraycopy(getDefaultKey(), 0, cur, length, 24 - length);
            curKey = cur;
        }
        return new DESedeKeySpec(curKey);
    }
}

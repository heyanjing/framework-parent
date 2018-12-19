package com.framework.commons.core.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.security.spec.KeySpec;

@Slf4j
public abstract class Crypt {
    private static byte[] DEFAULT_DES_KEY;
    private static javax.crypto.spec.IvParameterSpec IV8;
    private static javax.crypto.spec.IvParameterSpec IV16;

    static {
        DEFAULT_DES_KEY = DigestUtils.sha512Hex("猜?cDsd2-*-+e6!不HndaL@L-$#sdIH[D}sm找").getBytes();
        log.info("def_key:{},length=" + DEFAULT_DES_KEY.length, new String(DEFAULT_DES_KEY));
        byte[] ivData = new byte[16];
        System.arraycopy(DEFAULT_DES_KEY, 32, ivData, 0, 16);
        IV16 = new javax.crypto.spec.IvParameterSpec(ivData);
        ivData = new byte[8];
        System.arraycopy(DEFAULT_DES_KEY, 32, ivData, 0, 8);
        IV8 = new javax.crypto.spec.IvParameterSpec(ivData);
    }

    protected Type type = Type.DES;

    protected final static byte[] getDefaultKey() {
        return DEFAULT_DES_KEY;
    }

    public static Crypt getInstance(Type type) {
        Crypt crypt = null;
        if (type == Type.AES) {
            crypt = new AES();
        } else if (type == Type.DES) {
            crypt = new DES();
        } else if (type == Type.DES3) {
            crypt = new DES3();
        }
        return crypt;
    }

    public static void main(String[] args) {
        Crypt crypt = Crypt.getInstance(Type.AES);

        String source = "amigoxie";
        System.out.println("原文: " + source);
        String key = "A1B2C3D4E5F60708";
        String encryptData = crypt.encrypt0(source, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = crypt.decrypt0(encryptData, key);
        System.out.println("解密后: " + decryptData);

        encryptData = crypt.encrypt0(source);
        System.out.println("加密后: " + encryptData);
        decryptData = crypt.decrypt0(encryptData);
        System.out.println("解密后: " + decryptData);
    }

    public String encrypt0(String data) {
        return encrypt0(data, null);
    }

    public String encrypt0(String data, String key) {
        return Base64.encodeBase64String(encrypt0(data.getBytes(), key));
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public byte[] encrypt0(byte[] data, String key) {
        return encrypt0(type, data, key == null ? null : key.getBytes());
    }

    public String decrypt0(String data) {
        return decrypt0(data, null);
    }

    public String decrypt0(String data, String key) {
        return new String(decrypt0(Base64.decodeBase64(data), key));
    }

    /**
     * 解密数据
     */
    public byte[] decrypt0(byte[] data, String key) {
        return decrypt0(type, data, key == null ? null : key.getBytes());
    }

    protected abstract KeySpec keyGenerator(byte[] key) throws Exception;

    private SecretKey keyGenerator(byte[] key, Type type) throws Exception {
        byte[] curKey = key == null ? getDefaultKey() : key;
        /**AES*/
        if (type == Type.AES) {
            return (SecretKey) keyGenerator(curKey);
        }

        KeySpec keySpec = keyGenerator(curKey);

        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(type.getKey());
        SecretKey securekey = keyFactory.generateSecret(keySpec);
        return securekey;
    }

    private final byte[] encrypt0(Type type, byte[] data, byte[] key) {
        byte[] results = null;
        try {
            Key deskey = keyGenerator(key, type);
            // 实例化Cipher对象，它用于完成实际的加密操作
            Cipher cipher = Cipher.getInstance(type.getCipher());
//            SecureRandom random = new SecureRandom();
            // 初始化Cipher对象，设置为加密模式

            cipher.init(Cipher.ENCRYPT_MODE, deskey, type == Type.AES ? IV16 : IV8);
            results = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("encrypt err Type[{}]", type.getKey(), e);
        }
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return results;
    }

    /**
     * 解密数据
     */
    private final byte[] decrypt0(Type type, byte[] data, byte[] key) {
        try {
            Key deskey = keyGenerator(key, type);
            Cipher cipher = Cipher.getInstance(type.getCipher());
//            SecureRandom random = new SecureRandom();
            //初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, deskey, type == Type.AES ? IV16 : IV8);
            // 执行解密操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("decrypt err Type[{}]", type.getKey(), e);
            return null;
        }
    }

    public static enum Type {
        DES("DES", "DES/CBC/NoPadding"),
        DES3("DESede", "DESede/CBC/NoPadding"),
        AES("AES", "AES/CBC/PKCS5Padding"),
        SM4("SM4", "SM4/CBC/NoPadding"),
        ;

        //算法名称
        private String key;
        //算法名称/加密模式/填充方式
        //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
        private String cipher;

        Type(String key, String cipher) {
            this.key = key;
            this.cipher = cipher;
        }

        public String getKey() {
            return key;
        }

        public String getCipher() {
            return cipher;
        }
    }
}

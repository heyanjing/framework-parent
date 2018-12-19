package com.framework.commons.core.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by heyanjing on 2018/3/5 15:47.
 */
public class AESs {
    public static final String AES_KEY = "UThGCh2P989si+gCfRKN8A==";
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final String ENCRYPT_WAY = "AES/ECB/PKCS5Padding";

    public static SecretKey getSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        return keyGenerator.generateKey();
    }

    public static String getKeyBase64String() throws Exception {
        return Base64.encodeBase64String(getKeyByteArr());
    }

    private static byte[] getKeyByteArr() throws Exception {
        return getSecretKey().getEncoded();
    }

    /**
     * 加密
     *
     * @param content         内容
     * @param keyBase64String base64加密后的aeskey
     * @return 加密后的base64字符串
     * @throws Exception Exception
     */

    public static String encode(String content, String keyBase64String) throws Exception {
        return encode(content, Base64.decodeBase64(keyBase64String));
    }

    private static String encode(String content, byte[] keyByteArr) throws Exception {
        Key key = new SecretKeySpec(keyByteArr, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(content.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    public static String encodeDefault(String content) throws Exception {
        return encode(content, AES_KEY);
    }


    /**
     * 解密
     *
     * @param base64Content   base64加密后的内容
     * @param keyBase64String base64加密后的aeskey
     * @return 解密后的字符串
     * @throws Exception Exception
     */
    public static String dencode(String base64Content, String keyBase64String) throws Exception {
        return dencode(base64Content, Base64.decodeBase64(keyBase64String));
    }

    private static String dencode(String base64Content, byte[] keyByteArr) throws Exception {
        Key key = new SecretKeySpec(keyByteArr, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(base64Content));
        return new String(bytes);
    }

    public static String dencodeDefault(String base64Content) throws Exception {
        return dencode(base64Content, AES_KEY);
    }
}

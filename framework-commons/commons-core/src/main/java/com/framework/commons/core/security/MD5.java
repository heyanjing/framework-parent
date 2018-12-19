package com.framework.commons.core.security;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

public class MD5 {
    public static byte[] md5(final byte[] data) {
        return DigestUtils.md5(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
     *
     * @param data Data to digest
     * @return MD5 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] md5(final InputStream data) throws IOException {
        return DigestUtils.md5(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return MD5 digest
     */
    public static byte[] md5(final String data) {
        return DigestUtils.md5(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String md5Hex(final InputStream data) throws IOException {
        return DigestUtils.md5Hex(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String pwdMd5Hex(final String data) {
        if (data == null || "".equals(data)) {
            return data;
        }
        final int hashcode = Math.abs(data.hashCode());
        final int modulo = hashcode % 20;
        String pwdMd5Hex = data + hashcode;
        for (int i = 0; i < modulo; i++) {
            pwdMd5Hex = DigestUtils.md5Hex(pwdMd5Hex);
        }
        return pwdMd5Hex;
    }

    public static void main(String[] args) {
        System.out.println(pwdMd5Hex(md5Hex("test")));
    }
}

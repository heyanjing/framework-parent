package com.framework.commons.core.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

public class SHA {
    public static byte[] sha1(final byte[] data) {
        return DigestUtils.sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static byte[] sha1(final InputStream data) throws IOException {
        return DigestUtils.sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
     *
     * @param data
     * @return SHA-1 digest
     */
    public static byte[] sha1(final String data) {
        return DigestUtils.sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @since 1.7
     */
    public static String sha1Hex(final byte[] data) {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static String sha1Hex(final InputStream data) throws IOException {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @since 1.7
     */
    public static String sha1Hex(final String data) {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(final byte[] data) {
        return DigestUtils.sha256(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-256 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha256(final InputStream data) throws IOException {
        return DigestUtils.sha256(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(final String data) {
        return DigestUtils.sha256(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(final byte[] data) {
        return DigestUtils.sha256Hex(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha256Hex(final InputStream data) throws IOException {
        return DigestUtils.sha256Hex(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(final String data) {
        return DigestUtils.sha256Hex(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(final byte[] data) {
        return DigestUtils.sha384(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-384 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha384(final InputStream data) throws IOException {
        return DigestUtils.sha384(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(final String data) {
        return DigestUtils.sha384(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(final byte[] data) {
        return DigestUtils.sha384Hex(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha384Hex(final InputStream data) throws IOException {
        return DigestUtils.sha384Hex(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(final String data) {
        return DigestUtils.sha384Hex(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(final byte[] data) {
        return DigestUtils.sha512(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-512 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha512(final InputStream data) throws IOException {
        return DigestUtils.sha512(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(final String data) {
        return DigestUtils.sha512(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(final byte[] data) {
        return DigestUtils.sha512Hex(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha512Hex(final InputStream data) throws IOException {
        return DigestUtils.sha512Hex(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(final String data) {
        return DigestUtils.sha512Hex(data);
    }
}

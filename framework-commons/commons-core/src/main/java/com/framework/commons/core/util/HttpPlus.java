package com.framework.commons.core.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author heyanjing
 */
public class HttpPlus {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final String QUESTION_MARK = "?";
    private static final String AND_MARK = "&";
    private static final String EQUAL_MARK = "=";

    private static String getHttpGetResult(URI uri) throws IOException {
        return getHttpGetResult(uri.toString());
    }

    private static String getHttpGetResult(String uri) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(uri);
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } finally {
            release(client, response);
        }
    }

    private static String invokeHttpGet(String uri) throws IOException {
        return getHttpGetResult(uri);
    }

    private static String invokeHttpGet(String uri, Map<String, String> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(uri);
        if (!uri.contains(QUESTION_MARK)) {
            sb.append(QUESTION_MARK);
        }
        if (uri.contains(AND_MARK)) {
            sb.append(AND_MARK);
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append(EQUAL_MARK).append(entry.getValue()).append(AND_MARK);
        }
        String temp = sb.toString();
        String executeUri = temp.substring(0, temp.length() - 1);
        return getHttpGetResult(executeUri);
    }

    private static String invokeHttpPost(URI uri, Map<String, String> params) throws IOException {
        return invokeHttpPost(uri.toString(), params);
    }

    private static String getHttpPostResult(String uri, Map<String, String> params) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(generatePostEntity(params));
            response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } finally {
            release(client, response);
        }
    }

    private static String invokeHttpPost(String uri, Map<String, String> params) throws IOException {
        return getHttpPostResult(uri, params);
    }


    private static UrlEncodedFormEntity generatePostEntity(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(pairs, Consts.UTF_8);
    }

    private static void release(CloseableHttpClient client, CloseableHttpResponse response) throws IOException {
        if (client != null) {
            client.close();
        }
        if (response != null) {
            response.close();
        }
    }

    private static String invokeHttpPost(String scheme, String host, int port, String path, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path);
        return invokeHttpPost(builder.build(), params);
    }

    private static String invokeHttpGet(String scheme, String host, int port, String path, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return invokeHttpGet(builder.build());
    }

    private static String invokeHttpGet(URI uri) throws IOException {
        return getHttpGetResult(uri);
    }

    private static String invokeHttpGet(URI uri, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder().setScheme(uri.getScheme()).setHost(uri.getHost()).setPort(uri.getPort()).setPath(uri.getPath());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return getHttpGetResult(builder.build());
    }


    public static String invokeHttp(String method, URI uri, Map<String, String> params) throws IOException {
//        if (GET.equals(method)) {
//            if (params == null) {
//                return invokeHttpGet(uri);
//            } else {
//                return invokeHttpGet(uri, params);
//            }
//        } else if (POST.equals(method)) {
//            return invokeHttpPost(uri, params);
//        }
//        return null;
        return invokeHttp(method, uri.toString(), params);
    }

    public static String get(String uri) throws IOException {
        return invokeHttp(GET, uri, null);
    }

    public static String get(String uri, Map<String, String> params) throws IOException {
        return invokeHttp(GET, uri, params);
    }

    public static String post(String uri, Map<String, String> params) throws IOException {
        return invokeHttp(POST, uri, params);
    }

    private static String invokeHttp(String method, String uri, Map<String, String> params) throws IOException {
        if (GET.equals(method)) {
            if (params == null) {
                return invokeHttpGet(uri);
            } else {
                return invokeHttpGet(uri, params);
            }
        } else if (POST.equals(method)) {
            return invokeHttpPost(uri, params);
        }
        return null;
    }

    public static String invokeHttp(String method, String scheme, String host, int port, String path, Map<String, String> params) throws URISyntaxException, IOException {
        if (GET.equals(method)) {
            return invokeHttpGet(scheme, host, port, path, params);
        } else if (POST.equals(method)) {
            return invokeHttpPost(scheme, host, port, path, params);
        }
        return null;
    }
}

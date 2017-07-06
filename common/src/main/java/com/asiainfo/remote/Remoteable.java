package com.asiainfo.remote;

import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * Created by lenovo on 2017/6/22.
 */
public interface Remoteable {


    /**
     * 获取映射地址
     * @param serverName 注册服务名
     * @return 服务url
     */
    String getDiscoveryUrl(String serverName);

    /**
     * 远程调用获取资源
     *
     * @param serverName
     * @param clazz
     * @param resourcePath allow null
     * @param <T>
     * @return T
     */
    <T> T getCall(String serverName, Class<T> clazz, String... resourcePath);

    /**
     * 远程调用获取资源
     *
     * @param serverName
     * @param clazz
     * @param requestParam allow null
     * @param resourcePath allow null
     * @param <T>
     * @return
     */
    <T> T getCall(String serverName, Class<T> clazz, Map requestParam, String...
            resourcePath);

    /**
     * 远程调用获取资源
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getCall(String url, Class<T> clazz);

    /**
     * 远程新增资源
     *
     * @param serverName
     * @param requestBody
     * @param clazz
     * @param headers      allow null defult APPLICATION_JSON and utf-8
     * @param resourcePath allow null
     * @param <T>
     * @return
     */
    <T> T postCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders headers,
                   String... resourcePath);

    /**
     * 远程更新资源
     *
     * @param serverName
     * @param requestBody
     * @param clazz
     * @param headers      allow null defult APPLICATION_JSON and utf-8
     * @param resourcePath allow null
     * @param <T>
     * @return
     */
    <T> T putCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders headers,
                  String... resourcePath);

    /**
     * 远程更新资源
     *
     * @param serverName
     * @param requestBody
     * @param clazz
     * @param headers      allow null defult APPLICATION_JSON and utf-8
     * @param resourcePath allow null
     * @param <T>
     * @return
     */
    <T> T patchCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders headers,
                    String... resourcePath);

    /**
     * 远程删除资源
     *
     * @param serverName
     * @param clazz
     * @param headers      allow null defult APPLICATION_JSON and utf-8
     * @param resourcePath allow null
     * @param <T>
     * @return
     */
    <T> T deleteCall(String serverName, Class<T> clazz, HttpHeaders headers, String...
            resourcePath);

}

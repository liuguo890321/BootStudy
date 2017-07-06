package com.asiainfo.remote.impl;

 import com.asiainfo.remote.Remoteable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;


/**
 * Created by lenovo on 2017/6/22.
 */
@Component("restTemplateRemote")
public class RestTemplateRemote implements Remoteable {

    private static Logger logger = LoggerFactory.getLogger(RestTemplateRemote.class);

    private static HttpHeaders headers = new HttpHeaders();

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();


    public RestTemplateRemote(){
        List charsets = new ArrayList();
        charsets.add(Charset.forName("utf-8"));
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName
                ("utf-8")));




        headers.setAcceptCharset(charsets);
    }

    public <T> T getCall(String serverName, Class<T> clazz, String... resourcePath)
    {
        T resource;

        URI uri = this.createSpringCloudURL(serverName, null, resourcePath);
        resource = this.restTemplate.getForObject(uri, clazz);

        return resource;
    }

    public <T> T getCall(String serverName, Class<T> clazz, Map requestParam, String...
            resourcePath)
    {
        return this.content(serverName, null, clazz, HttpMethod.GET, headers, requestParam,
                resourcePath);
    }

    public <T> T getCall(String url, Class<T> clazz)
    {
        URI uri = URI.create(url.toString());
        T resource = this.restTemplate.getForObject(uri, clazz);
        return resource;
    }

    public <T> T postCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders
            headers, String... resourcePath)
    {
        return this.content(serverName, requestBody, clazz, HttpMethod.POST, headers, null,
                resourcePath);
    }

    public <T> T putCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders
            headers, String... resourcePath)
    {
        return this.content(serverName, requestBody, clazz, HttpMethod.PUT, headers, null,
                resourcePath);
    }

    public <T> T patchCall(String serverName, Object requestBody, Class<T> clazz, HttpHeaders
            headers, String... resourcePath)
    {
        return this.content(serverName, requestBody, clazz, HttpMethod.PATCH, headers,
                null, resourcePath);
    }

    public <T> T deleteCall(String serverName, Class<T> clazz, HttpHeaders headers, String...
            resourcePath)
    {
        return this.content(serverName, null, clazz, HttpMethod.DELETE, headers,
                null, resourcePath);
    }

    private <T> T content(String serverName, Object requestBody, Class<T> clazz, HttpMethod
            httpMethodValue, HttpHeaders headers, Map requestParam, String... resourcePath)
    {
        headers = (headers == null ? this.headers : headers);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> response;
        URI uri = this.createSpringCloudURL(serverName, requestParam, resourcePath);
        response = this.restTemplate.exchange
                (uri.toString(), httpMethodValue, requestEntity, clazz, new HashMap());

        return response.getBody();
    }



    private URI createSpringCloudURL(String serverName, Map requestParam, String... resourcePath)
    {
        StringBuilder url = new StringBuilder();
        serverName = getDiscoveryUrl(serverName);
        url = (resourcePath == null ?
                url.append(serverName) :
                url.append(serverName)
                        .append(String.join("/", resourcePath)).append("/"));
        this.dealRequestParam(url, requestParam);
        URI uri = URI.create(url.toString());
        logger.info("request url :" + uri);
        return uri;
    }

    public String getDiscoveryUrl(String serverName) {
        if(!serverName.startsWith("http")){
            serverName = "http://" + serverName;

        }
        if(!serverName.endsWith("/")){
            serverName = serverName + "/";
        }
        return  serverName;
    }

    private void dealRequestParam(StringBuilder url, Map requestParam)
    {
        if (requestParam != null && !requestParam.isEmpty())
        {
            Set<String> set = requestParam.keySet();
            String[] paramArry = new String[set.size()];
            int i = 0;
            for (String key : set)
            {
                paramArry[i] = key + "=" + requestParam.get(key);
                i = i + 1;
            }
            url.append("?" + String.join("&", paramArry));
        }
    }





}

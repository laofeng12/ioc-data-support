package com.ioc.datasupport.component;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * @author lsw
 */
@Component
public class OkHttpClientComponent {
    private static final Logger LOG = LoggerFactory.getLogger(OkHttpClientComponent.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @Resource
    private OkHttpClient okHttpClient;

    /**
     * get 请求
     * @param url 请求url地址
     * @return string
     * */
    public <T> T doGet(String url, Class<T> clazz) {
        return doGet(url, null, null, clazz);
    }

    /**
     * get 请求
     *
     * @param url url
     * @param params 参数
     * @param headers 头部
     * @return 响应
     */
    public String doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        return doGet(url, params, headers, String.class);
    }

    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @param headers   请求头字段
     * @return string
     * */
    public <T> T doGet(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        if (params == null) {
            params = Collections.emptyMap();
        }
        if (headers == null) {
            headers = Collections.emptyMap();
        }

        // 构建URL
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
        // 处理参数
        params.forEach(urlBuilder::queryParam);
        // 构建URL
        url = urlBuilder.build().encode().toString();

        Request.Builder reqBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                reqBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // 设置长连接为close
        reqBuilder.addHeader("Connection", "close");

        return execute(reqBuilder.url(url).build(), clazz);
    }

    public String doPostForm(String url, Map<String, String> params) {
        return doPostForm(url, params, String.class);
    }

    /**
     * post 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    public <T> T doPostForm(String url, Map<String, String> params, Class<T> clazz) {
        FormBody.Builder builder = new FormBody.Builder();

        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                // 设置长连接为close
                .addHeader("Connection", "close")
                .url(url)
                .post(builder.build())
                .build();

        return execute(request, clazz);
    }

    public String doPostJson(String url, String json, Map<String, String> headers) {
        return doPostJson(url, json, headers, String.class);
    }

    public String doPostJson(String url, Map<String, Object> params, Map<String, String> headers) {
        if (params == null) {
            params = Collections.emptyMap();
        }
        String json = JSONObject.toJSONString(params);

        return doPostJson(url, json, headers);
    }

    public <T> T doPostJson(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        if (params == null) {
            params = Collections.emptyMap();
        }
        String json = JSONObject.toJSONString(params);

        return doPostJson(url, json, headers, clazz);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url       请求url地址
     * @param json      请求数据, json 字符串
     * @return string
     */
    public <T> T doPostJson(String url, String json, Map<String, String> headers, Class<T> clazz) {
        return executePost(url, json, headers, JSON, clazz);
    }

    public String doPostXml(String url, String xml, Map<String, String> headers) {
        return doPostXml(url, xml, headers, String.class);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url       请求url地址
     * @param xml       请求数据, xml 字符串
     * @return string
     */
    public <T> T doPostXml(String url, String xml, Map<String, String> headers, Class<T> clazz) {
        return executePost(url, xml, headers, XML, clazz);
    }

    /**
     * 执行post
     *
     * @param url URL
     * @param data 数据
     * @param contentType 内容类型
     * @param clazz 类
     * @param <T> 泛型
     * @return 实体类
     */
    private <T> T executePost(String url, String data, Map<String, String> headers, MediaType contentType, Class<T> clazz) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder reqBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                reqBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // 设置长连接为close
        reqBuilder.addHeader("Connection", "close");

        Request request = reqBuilder.url(url).post(requestBody).build();
        return execute(request, clazz);
    }

    /**
     * 执行
     *
     * @param request 请求
     * @param clazz 目标实体类
     * @param <T>   泛型
     * @return 目标实体对象
     */
    private <T> T execute(Request request, Class<T> clazz) {
        String body = execute(request);
        if (StringUtils.isBlank(body)) {
            return null;
        }

        return JSONObject.parseObject(body, clazz);
    }

    /**
     * 发送请求
     *
     * @param request 请求参数
     * @return 响应
     */
    private String execute(Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() == null) {
                    return "";
                }

                return response.body().string();
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }

        return "";
    }
}

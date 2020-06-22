package com.ioc.datasupport.conf;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author: lsw
 * @Date: 2020/5/14 14:37
 */
@Configuration
public class OkHttpConfiguration {
    /** 连接超时时间：秒 */
    @Value("${ok.http.connect-timeout}")
    private Integer connectTimeout;

    /** 读数据超时时间：秒 */
    @Value("${ok.http.read-timeout}")
    private Integer readTimeout;

    /** 写数据超时时间：秒 */
    @Value("${ok.http.write-timeout}")
    private Integer writeTimeout;

    /** 连接池中整体的空闲连接的最大数量 */
    @Value("${ok.http.max-idle-connections}")
    private Integer maxIdleConnections;

    /** 连接空闲时间最多为 300 秒 */
    @Value("${ok.http.keep-alive-duration}")
    private Long keepAliveDuration;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                // 是否开启缓存
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                // 设置代理
//            	.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                // 拦截器
//                .addInterceptor()
                // 设置connect失败后重试
                .retryOnConnectionFailure(true)
                .build();
    }

    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Bean
    public SSLSocketFactory sslSocketFactory() {
        try {
            // 信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }
}

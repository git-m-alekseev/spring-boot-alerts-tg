package dev.max.demo.alerts.tg.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.httpclient5.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient5.LogbookHttpResponseInterceptor;

import static dev.max.demo.alerts.tg.integration.alertmanager.AlertManagerClientImpl.ALERT_MANAGER_REST_CLIENT;

@Configuration
public class RestClientConfig {

    @Bean(ALERT_MANAGER_REST_CLIENT)
    public RestClient.Builder alertmanagerRestClient(AlertManagerProperties properties, Logbook logbook) {
        var requestConfig = RequestConfig.custom()
                .setConnectionKeepAlive(TimeValue.of(properties.getKeepAliveTimeout()))
                .setResponseTimeout(Timeout.of(properties.getReadTimeout()))
                .setConnectionRequestTimeout(Timeout.of(properties.getConnectTimeout()))
                .build();

        var httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .addRequestInterceptorLast(new LogbookHttpRequestInterceptor(logbook))
                .addResponseInterceptorLast(new LogbookHttpResponseInterceptor())
                .build();

        var factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return RestClient.builder().requestFactory(factory);
    }
}
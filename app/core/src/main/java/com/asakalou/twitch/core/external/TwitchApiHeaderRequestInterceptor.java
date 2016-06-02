package com.asakalou.twitch.core.external;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;


public class TwitchApiHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    public static final String CLIENT_ID = "fto9hteui1hcmzc0x8h0ygdhnir846s";
    public static final String CLIENT_HEADER_NAME = "Client-ID";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(request);
        wrapper.getHeaders().set(CLIENT_HEADER_NAME, CLIENT_ID);
        return execution.execute(wrapper, body);

    }
}

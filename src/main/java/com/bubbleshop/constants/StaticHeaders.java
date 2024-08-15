package com.bubbleshop.constants;

import org.springframework.http.HttpHeaders;

public class StaticHeaders {
    // 헤더값
    public static final String BACKOFFICE_CHANNEL = "BO";
    public static final String BACKOFFICE_CHANNEL_HEADER = HttpHeaders.FROM + "=" + BACKOFFICE_CHANNEL;
}

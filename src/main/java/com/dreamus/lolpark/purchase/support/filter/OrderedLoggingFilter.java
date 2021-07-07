package com.dreamus.lolpark.purchase.support.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;

public class OrderedLoggingFilter extends LoggingFilter implements OrderedFilter {

    //OrderedHiddenHttpMethodFilter -10000
    public static final int DEFAULT_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER - 10001;

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}

package com.weilus.commons.feign.interceptors;

import feign.auth.BasicAuthRequestInterceptor;

/**
 * Created by liutq on 2019/3/15.
 */
public class AuthFeignServiceInterceptor extends BasicAuthRequestInterceptor{

    public AuthFeignServiceInterceptor() {
        super("fadffsedfsf","rrfdaDSFfesnmhf");
    }
}

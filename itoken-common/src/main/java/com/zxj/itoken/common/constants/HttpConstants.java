package com.zxj.itoken.common.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author zxj
 * @date 2020/06/21
 */
@Getter
public enum HttpConstants {
    /**
     * 从上游服务器接收到无效的响应
     */
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY.value(), "从上游服务器接收到无效的响应");

    private int code;
    private String message;

    // 构造器
    private HttpConstants (int code, String message){
        this.code = code;
        this.message = message;
    }



}

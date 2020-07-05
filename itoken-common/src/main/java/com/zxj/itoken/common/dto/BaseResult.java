package com.zxj.itoken.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author zxj
 * @date 2020/06/20
 *
 * 数据传输对象
 */
@Data
public class BaseResult implements Serializable {
    private static final String RESULT_OK = "ok";
    private static final String RESULT_NOT_OK = "not_ok";
    private static final String SUCCESS = "操作成功";

    // 包括两种状态：ok/not_ok，不为空；
    private String result;
    // 返回具体的数据，可为空；默认为空Object，存在数据时，不一定为Object，可能会使Array；
    private Object data;
    // 返回所包含的数据，可为空；默认为空Object，结构固定；
    private Cursor cursor;
    // 成功返回信息，可为空；默认为空字符""，存储的是一个对象，结构固定；
    private String success;
    // 错误返回信息，可为空；默认为空Array，里面存对象，结构固定；
    private List<Error> errors;


    public static BaseResult ok(){
        return createResult(RESULT_OK, null, SUCCESS, null, null);
    }
    public static BaseResult ok(Object data){
        return createResult(RESULT_OK, data, SUCCESS, null, null);
    }
    public static BaseResult ok(Object data, Cursor cursor){
        return createResult(RESULT_OK, data, SUCCESS, cursor, null);
    }
    public static BaseResult ok(String success) {
        return createResult(RESULT_OK, null, success, null, null);
    }


    public static BaseResult notOk(List<BaseResult.Error> errors){
        return createResult(RESULT_NOT_OK, null, "", null, errors);
    }

    /**
     * @param result
     * @param data
     * @param cursor
     * @param success
     * @param errors
     * @return
     */
    private static BaseResult createResult(String result, Object data, String success, Cursor cursor, List<Error> errors){
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setCursor(cursor);
        baseResult.setSuccess(success);
        baseResult.setErrors(errors);
        return baseResult;
    }



    // 内部类：做分页用
    // 这是一个对象，里面有三个属性，所以使用内部类；
    @Data
    public static class Cursor{
        private int total;  // 全部条数
        private int offset; // 当前所在位置
        private int limit;  // 每页条数
    }
    @Data
    @AllArgsConstructor  // 全部参数的构造函数
    public static class Error{
        private String field;
        private String message;
    }
}

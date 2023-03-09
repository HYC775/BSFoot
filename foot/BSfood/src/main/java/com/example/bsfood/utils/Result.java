package com.example.bsfood.utils;

import lombok.Data;

import java.util.HashMap;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {
    private Boolean success;//是否成功
    private Integer code;//状态码
    private String message;//返回消息
    private T data;//返回数据

    /**
     * 私有化构造方法，禁止在其它类创建对象
     */
    private Result(){}

    /**
     * 成功执行，不返回数据
     * @return
     */
    public static<T> Result<T> ok(){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功");
        return result;
    }


    /**
     * 成功执行，返回自定义字符串
     * @return
     */
    public static<T> Result<T> ok(String message){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功执行，并返回数据
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功");
        result.setData(data);
        return result;
    }

    /**
     * 失败
     * @return
     */
    public static<T> Result<T> error(){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("执行失败");
        return result;
    }


    public static Result error(int code,String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 失败
     * @return
     */
    public static<T> Result<T> error(String message){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage(message);
        return result;
    }
    /**
     * 设置是否成功
     * @param success
     * @return
     */
    public Result<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置状态码
     * @param code
     * @return
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 设置返回消息
     * @param message
     * @return
     */
    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 是否存在
     * @return
     */
    public static<T> Result<T> exist(){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功");
        return result;
    }

    public HashMap put(String key, Object value) {
        HashMap hashMap =new HashMap();
        hashMap.put(key, value);
        return hashMap;
    }
}
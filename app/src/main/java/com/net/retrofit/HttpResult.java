package com.net.retrofit;

/**
 * Created by helin on 2016/10/10 11:44.
 * 实体的基类
 */
public class HttpResult<T> {

    public boolean success;
    public String msg;
    public int code ;
    public T list;
    public T data;

}

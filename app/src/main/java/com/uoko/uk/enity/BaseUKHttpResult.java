package com.uoko.uk.enity;

/**
 * 作者: xwb on 2017/12/12
 * 描述: 基类
 */

public class BaseUKHttpResult {

    private final int SUCCESS_CODE = 200;

    public String msg;
    public int code;


    public boolean isSuccess(){
        return code==SUCCESS_CODE;
    }
}

package com.org.androidtools.network.bean;

import java.io.Serializable;

/**
 * Created by wanglin on 2015/8/5.
 */
public class ResultInfo implements Serializable {

    private int status;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

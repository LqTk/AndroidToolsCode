package com.org.androidtools.network;

import com.org.androidtools.network.bean.ResultInfo;

import java.io.Serializable;

/**
 * @author tian
 * @desc 实体类基类
 * @date 2017/10/24 16:04
 */
public class BaseInfo implements Serializable {

    /**
     * 是否处理成功状态消息体
     */
    private ResultInfo result;

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "result=" + result +
                '}';
    }
}

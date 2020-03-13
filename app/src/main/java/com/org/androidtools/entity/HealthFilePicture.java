package com.org.androidtools.entity;


import java.io.Serializable;

/**
 * Created by yangpeng on 2016/3/4.
 */
public class HealthFilePicture implements Serializable {
    public String id;
    public String url;//图片本地地址
    public int state=2;//0,未上传成功，1、上传成功，2服务器图片,3正在上传
    public String thumbnails;//缩略图地址
}

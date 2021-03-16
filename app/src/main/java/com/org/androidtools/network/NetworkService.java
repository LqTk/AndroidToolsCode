package com.org.androidtools.network;

import com.org.androidtools.entity.FileUploadResponseVo;
import com.org.androidtools.network.bean.RegisterEntity;
import com.org.androidtools.network.bean.ResultInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkService {
    public static String BaseUrl = "http://192.168.2.174:8088";

    /**
     * 上传头像
     * Form表单上传数据
     * platform/app/pic/uploadByUserId
     * 参数	类型	说明
     * userId	string	用户ID
     * pic	File	图片文件
     * picType	string	图片类型 USER_AVATAR：用户头像
     */
    @Multipart
    @POST("platform/app/pic/uploadByUserId")
    Observable<FileUploadResponseVo> upLoadAvatar(@Part("userId") RequestBody userId, @Part("picType") RequestBody picType, @Part MultipartBody.Part part);

    @POST("/seller/register")
    Observable<RegisterEntity> loginInfo(@Body Map<String,String> user);
}

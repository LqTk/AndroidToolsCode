package com.org.androidtools.network;

import com.org.androidtools.entity.FileUploadResponseVo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkService {
    public static String BaseUrl = "https://app-access.synwing.com:8443/health_app_v2/";

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
}

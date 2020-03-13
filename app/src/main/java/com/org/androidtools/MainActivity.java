package com.org.androidtools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.org.androidtools.entity.FileUploadResponseVo;
import com.org.androidtools.network.NetworkService;
import com.org.androidtools.permission.Permissions;
import com.org.androidtools.picture.Pictures;
import com.org.androidtools.retrofit.HttpMethod;
import com.org.androidtools.rxjava.BaseObserver;
import com.org.androidtools.rxjava.RxSchedulers;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_takepic)
    Button btnTakepic;
    @BindView(R.id.btn_pic)
    Button btnPic;
    private Unbinder unbinder;
    private NetworkService service;

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        service = HttpMethod.getInstance().create(NetworkService.class);

//        upLoadPic();
    }

    /**
     * 上传图片
     */
    private void upLoadPic() {
        String userId = "";
        String pathFile = "";
        String picType = "USER_AVATAR";

        File file = new File(pathFile);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
        service.upLoadAvatar(toRequestBody(userId), toRequestBody(picType), part)
                .compose(RxSchedulers.<FileUploadResponseVo>compose(getApplicationContext()))
                .subscribe(new BaseObserver<FileUploadResponseVo>(getApplicationContext()) {
                    @Override
                    public void onSuccess(FileUploadResponseVo fileUploadResponseVo) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }


    /**
     * 字符串转RequestBody，用于retrofit图文上传
     *
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    @OnClick({R.id.btn_takepic, R.id.btn_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_takepic:
                rxPermissions.request(Permissions.permissionPhotos)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                Log.d("execting","222222222"+aBoolean);
                                if (aBoolean){
                                    Pictures.takePhoto(MainActivity.this);
                                }else {
                                    Toast.makeText(MainActivity.this,"权限请求失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.btn_pic:

                /*
                //申请单个权限
                rxPermissions.setLogging(true);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                LogUtils.error(TAG, "checkPermission22--:" + aBoolean);
                            }
                        });*/
                //申请多个权限获取详细信息
                /*rxPermissions.requestEach(Permissions.permissionPhotos)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                Boolean aBoolean = true;
                                if (permission.name.equalsIgnoreCase(Permissions.permissionPhotos[0])){
                                    if (!permission.granted){
                                        aBoolean = false;
                                    }
                                }else if (permission.name.equalsIgnoreCase(Permissions.permissionPhotos[1])){
                                    if (!permission.granted){
                                        aBoolean = false;
                                    }
                                }else if (permission.name.equalsIgnoreCase(Permissions.permissionPhotos[2])){
                                    if (!permission.granted){
                                        aBoolean = false;
                                    }
                                }
                                if (aBoolean){
//                                    Pictures.selectPic(MainActivity.this);
                                    PictureSelector.create(MainActivity.this)
                                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                            .previewImage(true)
                                            .maxSelectNum(5)// 最大图片选择数量 int
                                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                            .minSelectNum(5)// 最小选择数量 int
                                            .imageSpanCount(3)// 每行显示个数 int
                                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                            .isCamera(false)// 是否显示拍照按钮 true or false
                                            .enableCrop(true)
                                            .isDragFrame(true)// 是否可拖动裁剪框
                                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                            .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                            .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                                }else {
                                    Toast.makeText(MainActivity.this,"权限请求失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                rxPermissions.request(Permissions.permissionPhotos)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean){
                                    Pictures.selectPic(MainActivity.this);
                                }
                            }
                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    //LocalMedia media = list.get(position);
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 原图path
                    // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                    // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    for (LocalMedia media : selectList) {
                       /* Log.i(TAG, "是否压缩:" + media.isCompressed());
                        Log.i(TAG, "压缩:" + media.getCompressPath());
                        Log.i(TAG, "原图:" + media.getPath());
                        Log.i(TAG, "是否裁剪:" + media.isCut());
                        Log.i(TAG, "裁剪:" + media.getCutPath());
                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                        Log.i(TAG, "Size: " + media.getSize());*/
                    }
//                    mAdapter.setList(selectList);
//                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

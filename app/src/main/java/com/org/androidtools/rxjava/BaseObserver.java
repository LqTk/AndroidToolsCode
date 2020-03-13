package com.org.androidtools.rxjava;

import android.content.Context;
import android.widget.Toast;

import com.org.androidtools.network.BaseInfo;
import com.org.androidtools.network.util.DateTimeUtil;
import com.org.androidtools.network.util.GsonUtil;
import com.org.androidtools.network.util.NetworkStatusManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    private Context context;
    private String message="";

    public BaseObserver() {
    }

    public BaseObserver(Context context) {
        this.context = context;
        message = "";
    }

    public BaseObserver(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public void onSubscribe(Disposable d) {
         if (context != null){
             if (NetworkStatusManager.getInstance().detectNetwork(context) == NetworkStatusManager.NETWORK_CLASS_UNKNOWN){
                 onFailure("请检查网络");
                 d.dispose();
                 return;
             }
         }
    }

    @Override
    public void onNext(T t) {
        String json = GsonUtil.objectToJson(t);
        BaseInfo info = GsonUtil.fromJson(json,BaseInfo.class);
        if (info.getResult().getStatus() == 1){
            onSuccess(t);
        }else{
            String msg = info.getResult().getMessage();
            onFailure(msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (context != null && !(e instanceof ConnectException) && !(e instanceof SocketTimeoutException)){
            Toast.makeText(context.getApplicationContext(), DateTimeUtil.getCurrentTime()+" "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        onFailure("网络错误");
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(String msg);

}

package com.org.androidtools.designmodel.singletion;

/**
 * 单例模式
 */
public class SingletionDes {
    private static SingletionDes singletionDes;

    public SingletionDes() {
    }

    public static SingletionDes getInstance(){
        synchronized (SingletionDes.class){
            if (singletionDes==null){
                singletionDes = new SingletionDes();
            }
        }
        return singletionDes;
    }
}

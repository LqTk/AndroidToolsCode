package com.org.androidtools.designmodel.factory;

/**
 * 普通抽象类工厂，抽象工厂就是内部包含抽象类接口
 */
public abstract class Factory {
    public abstract <T extends NokiaPhone> T createNokia(Class<T> cla);
}

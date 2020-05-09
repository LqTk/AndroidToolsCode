package com.org.androidtools.designmodel.factory;

/**
 * 定义工厂
 */
public class NokiaFactory extends Factory {
    @Override
    public <T extends NokiaPhone> T createNokia(Class<T> cla) {
        NokiaPhone nokiaPhone = null;
        try {
            /**
             * 通过反射方法实现抽象类，通过类名(ClassName)来实例化具体的类
             */
            nokiaPhone = (NokiaPhone) Class.forName(cla.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) nokiaPhone;
    }
}


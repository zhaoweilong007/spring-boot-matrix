package com.zwl.delayed;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/24 10:39
 **/
public interface DelayedServiceHandler<T> {

    void execute(T t);
}

package com.zwl.read;

import com.alibaba.fastjson2.JSON;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 描述： 自定义json读取
 *
 * @author zwl
 * @since 2022/6/22 17:08
 **/
public class FastJsonItemRead<T> implements JsonObjectReader<T> {

    private InputStream inputStream;

    private final Type type;

    public FastJsonItemRead(Type type) {
        this.type = type;
    }

    @Override
    public void open(Resource resource) throws Exception {
        Assert.notNull(resource, "The resource must not be null");
        this.inputStream = resource.getInputStream();
    }

    @Override
    public T read() {
        try {
            return JSON.parseObject(inputStream, type);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }

}

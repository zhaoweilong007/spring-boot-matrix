package com.zwl.test;

import com.alibaba.fastjson.JSON;
import com.zwl.model.LMessage;
import org.junit.Test;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 18:05
 **/

public class RedisTest {


    @Test
    public void test(){
        String json = "{\n" +
                "\t\"channel\":\"queueA\",\n" +
                "\t\"count\":0.48,\n" +
                "\t\"headers\":{},\n" +
                "\t\"lists\":[\n" +
                "\t\t\"4xg56\",\n" +
                "\t\t\"aktuv\",\n" +
                "\t\t\"0cav9\",\n" +
                "\t\t\"dobi6\",\n" +
                "\t\t\"ddxe4\",\n" +
                "\t\t\"qnetz\",\n" +
                "\t\t\"o6ozy\",\n" +
                "\t\t\"jlfsb\",\n" +
                "\t\t\"dc88e\",\n" +
                "\t\t\"abkz0\"\n" +
                "\t],\n" +
                "\t\"name\":\"msg=0\"\n" +
                "}";
        LMessage lMessage = JSON.parseObject(json, LMessage.class);
        System.out.println(lMessage);
    }

}

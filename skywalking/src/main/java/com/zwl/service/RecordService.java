package com.zwl.service;

import com.alibaba.fastjson.JSON;
import com.zwl.entity.Record;
import com.zwl.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/11 14:11
 **/
@Service
@Slf4j
public class RecordService {

    @Autowired
    RecordRepository repository;

    @Autowired
    StringRedisTemplate redisTemplate;

    public Record cacheRedis(String id) {
        log.info("invoke cacheRedis :{} ", id);
        String val = redisTemplate.opsForValue().get(id);
        if (val != null) {
            log.info("val is null...");
            return JSON.parseObject(val, Record.class);
        }
        Optional<Record> optional = repository.findById(Long.parseLong(id));
        Record record = optional.orElseThrow(() -> new IllegalArgumentException("id不存在"));
        redisTemplate.opsForValue().set(id, JSON.toJSONString(record));
        log.info("invoke  cacheRedis result  :{}", record);
        return record;
    }
}

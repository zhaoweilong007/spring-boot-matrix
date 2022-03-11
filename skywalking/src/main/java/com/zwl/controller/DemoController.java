package com.zwl.controller;

import com.zwl.entity.Record;
import com.zwl.repository.RecordRepository;
import com.zwl.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/11 13:58
 **/
@RestController
@Slf4j
public class DemoController {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordService recordService;

    @GetMapping
    public List<Record> queryList() {
        log.info("query List invoke....");
        List<Record> all = repository.findAll();
        log.info("query list result :{}",all);
        return all;
    }

    @GetMapping("id/{id}")
    public Record getByuId(@PathVariable("id") String id) {
        return recordService.cacheRedis(id);
    }

}

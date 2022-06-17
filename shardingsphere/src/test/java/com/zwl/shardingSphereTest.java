package com.zwl;

import cn.hutool.db.Db;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.zwl.entity.Answer;
import com.zwl.mapper.AnswerMapper;
import com.zwl.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class shardingSphereTest {

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    AnswerService answerService;

    private List<Answer> list = Lists.newArrayList();

    @Before
    public void init() throws SQLException {
        list = JSON.parseArray(JSON.toJSONString(Db.use().findAll("answer")), Answer.class);
        log.info("init answer list,size:{}", list.size());
    }

    // todo 测试插入数据分库分表
    @Test
    public void testInsert() {
        list.forEach(answer -> answer.setId(null));
        boolean batch = answerService.saveBatch(list);
        log.info("批量插入：{}", batch);

        list.forEach(
                answer ->
                        log.info(
                                "topicId:{} % 2 = {},answerId:{} % 2 = {}",
                                answer.getTopicId(),
                                answer.getTopicId() % 2,
                                answer.getAnswerId(),
                                answer.getAnswerId() % 2));
    }

    @Test
    public void testSelect() {
        Random random = new Random();
        int index = random.nextInt(list.size() - 1);
        Answer answer = list.get(index);
        Answer result = answerService.getOne(Wrappers.<Answer>lambdaQuery()
                .eq(Answer::getTopicId, answer.getTopicId())
                .eq(Answer::getAnswerId, answer.getAnswerId())
                .eq(Answer::getQuestionId, answer.getQuestionId()));
        Assert.assertEquals(answer.getAnswerId(), result.getAnswerId());
        log.info("查询结果：{}", result);
    }
}

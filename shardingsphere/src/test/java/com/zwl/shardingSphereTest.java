package com.zwl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zwl.entity.Answer;
import com.zwl.mapper.AnswerMapper;
import com.zwl.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class shardingSphereTest {

  @Autowired AnswerMapper answerMapper;

  @Autowired AnswerService answerService;

  // todo 测试插入数据分库分表
  @Test
  public void test() throws SQLException {
    List<Entity> answers = Db.use().findAll("answer");
    int size = answers.size();
    log.info("size:{}", size);
    log.info("first row:{}", Optional.ofNullable(answers.get(0)).orElse(null));

    List<Answer> list = JSON.parseArray(JSON.toJSONString(answers), Answer.class);
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

    List<Answer> answerList =
        answerService.list(Wrappers.<Answer>lambdaQuery().eq(Answer::getAnswerId, 920583702));

    log.info(
        "answerList:{}", answerList.stream().map(Answer::getAnswerId).collect(Collectors.toList()));
  }
}

package com.zwl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zwl.dao.AnswerDao;
import com.zwl.dao.AnswerDao2;
import com.zwl.entity.AnswerEntity;
import com.zwl.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 */
@SpringBootTest(classes = MultiDataSourceApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SpringJpaTest {

  @Autowired AnswerDao answerDao;

  @Autowired AnswerDao2 answerDao2;

  @Autowired AnswerService answerService;

  @Test
  public void test() {
    AnswerEntity answerEntity = answerService.findByIdMaster(1);
    log.info("query master db :{}", answerEntity);

    AnswerEntity answer = answerService.findByIdSlave(1);
    log.info("query slave_1 db:{}", answer);

    Assert.assertNotEquals(answerEntity.getQuestion(), answer.getQuestion());

    Wrapper<AnswerEntity> wrapper =
        new LambdaQueryWrapper<AnswerEntity>().like(AnswerEntity::getQuestion, "为什么");

    List<AnswerEntity> master = answerService.findMaster(wrapper);

    List<AnswerEntity> slave = answerService.findSlave(wrapper);

    Assert.assertNotEquals(master.size(), slave.size());
  }
}

package com.zwl;

import cn.hutool.core.lang.Console;
import com.zwl.entity.Answer;
import com.zwl.repository.AnswerRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 **/
@RunWith(SpringRunner.class)
@SpringBootApplication
@Slf4j
public class MongoDBTest {


  @Autowired
  AnswerRepository answerRepository;

  @Autowired
  MongoTemplate mongoTemplate;


  @Test
  public void test() {
    log.info("hello");
  }

}

package com.zwl;

import com.zwl.dao.AnswerDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 **/
@SpringBootTest(classes = MultiDataSourceApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SpringJpaTest {

  @Autowired
  AnswerDao answerDao;

  @Test
  public void test() {
    System.out.println(answerDao.findById(1L));
  }

}

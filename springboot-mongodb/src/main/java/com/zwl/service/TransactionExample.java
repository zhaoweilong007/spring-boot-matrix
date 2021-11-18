package com.zwl.service;

import com.zwl.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/15
 */
@Service
public class TransactionExample {

  @Autowired MongoTemplate mongoTemplate;

  @Transactional(rollbackFor = Exception.class)
  public Object insert() {

    Answer answer = new Answer();
    answer.setTopicId(0);
    answer.setAnswerId(0L);
    answer.setQuestionId(0L);
    answer.setAnswerUrl("test");
    answer.setQuestion("test");
    answer.setVoteupCount(0);
    answer.setExcerpt("test");
    answer.setAuthorName("test");
    answer.setCreateDate(new Date());
    answer.setIsGodReplies(false);

    Answer result = mongoTemplate.insert(answer);

    int i = 1 / 0;

    return result;
  }
}

package com.zwl.repository;

import com.zwl.entity.Answer;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 **/
public interface AnswerRepository extends MongoRepository<Answer, String> {

  List<Answer> findAnswersByQuestion(String title);

  List<Answer> findAnswersByAuthorName(String author);

  default List<Answer> findByQuest(String wordKey) {
    Answer answer = new Answer();
    answer.setQuestion(wordKey);
    Example<Answer> example = Example.of(answer,
        ExampleMatcher.matching().withMatcher("question", GenericPropertyMatchers.contains()));
    return findAll(example);
  }

}

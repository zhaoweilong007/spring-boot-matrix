package com.zwl.repository;

import com.zwl.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 **/
public interface AnswerRepository extends MongoRepository<Answer, String> {

}

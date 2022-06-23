package com.zwl.dao;

import com.zwl.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 */
public interface AnswerDao extends JpaRepository<Answer, Integer> {}

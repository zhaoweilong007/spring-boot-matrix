package com.zwl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwl.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 **/

@Mapper
public interface AnswerDao2 extends BaseMapper<AnswerEntity> {

  @Select("select * from answer where id=#{id}")
  AnswerEntity findById(Integer id);


}

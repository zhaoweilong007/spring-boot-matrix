package com.zwl.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwl.dao.AnswerDao2;
import com.zwl.entity.AnswerEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/5
 **/
@Service
public class AnswerService extends ServiceImpl<AnswerDao2, AnswerEntity> {

  @DS("master")
  public AnswerEntity findByIdMaster(Integer id) {
    return baseMapper.findById(id);
  }

  @DS("slave_1")
  public AnswerEntity findByIdSlave(Integer id) {
    return baseMapper.findById(id);
  }


  public List<AnswerEntity> findMaster(Wrapper<AnswerEntity> wrapper) {
    return baseMapper.selectList(wrapper);
  }

  @DS("slave_1")
  public List<AnswerEntity> findSlave(Wrapper<AnswerEntity> wrapper) {
    return baseMapper.selectList(wrapper);
  }

  ;
}

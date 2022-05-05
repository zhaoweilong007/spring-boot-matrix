package com.zwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwl.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Entity com.zwl.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

  @Update("update user set amount=amount-#{amount} where id=#{userId}")
  Boolean deductBalance(@Param("userId") Integer userId, @Param("amount") Double amount);
}

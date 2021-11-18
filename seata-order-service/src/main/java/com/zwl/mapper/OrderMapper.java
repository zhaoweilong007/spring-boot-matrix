package com.zwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwl.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/** @Entity com.zwl.domain.Order */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {}

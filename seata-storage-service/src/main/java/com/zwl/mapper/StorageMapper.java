package com.zwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwl.domain.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Entity com.zwl.domain.Storage
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

  @Update("update storage set stock=stock-#{num},update_time=now() where id=#{storageId}")
  Boolean deductStorage(@Param("storageId") Integer storageId, @Param("num") Integer num);
}

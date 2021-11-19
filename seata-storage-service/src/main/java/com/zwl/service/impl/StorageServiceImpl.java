package com.zwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwl.domain.Storage;
import com.zwl.mapper.StorageMapper;
import com.zwl.service.StorageRpcService;
import com.zwl.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/** */
@Service
@DubboService(version = "1.0.0",interfaceClass = StorageRpcService.class)
@Slf4j
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage>
    implements StorageService, StorageRpcService {
  @Override
  public Integer queryStock(Integer storageId) {
    return Optional.ofNullable(baseMapper.selectById(storageId).getStock()).orElse(null);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deductStorage(Integer storageId, Integer num) {
    final Integer stock = queryStock(storageId);
    if (stock == null || stock < num) {
      log.warn("库存余额不足：{}", stock);
      throw new RuntimeException("库存余额不足");
    }
    return baseMapper.deductStorage(storageId, num);
  }
}

package com.zwl.service;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/12
 */
public interface StorageRpcService {

  Integer queryStock(Integer storageId);

  Boolean deductStorage(Integer storageId, Integer num);
}

package com.zwl;

import com.zwl.mapper.StorageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageTest {

  @Autowired StorageMapper storageMapper;

  @Test
  public void test() {
    storageMapper.deductStorage(1, 4);
  }
}

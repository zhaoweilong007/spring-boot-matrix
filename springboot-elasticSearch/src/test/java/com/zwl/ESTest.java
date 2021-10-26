package com.zwl;

import cn.hutool.core.lang.Console;
import java.io.IOException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {

  @Autowired
  RestHighLevelClient restHighLevelClient;

  /**
   * 索引测试
   */
  @Test
  public void index() throws IOException {
    // 创建索引
    IndexResponse response =
        restHighLevelClient.index(new IndexRequest("zwl"), RequestOptions.DEFAULT);
    Console.log("创建索引:{}", response);
  }

  /**
   * 文档测试
   */
  @Test
  public void document() {
  }

  /**
   * 查询相关信息we
   */
  @Test
  public void info() {
  }
}

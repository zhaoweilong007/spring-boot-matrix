package com.zwl;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.zwl.model.BlogModel;
import com.zwl.repository.BlogRepository;
import java.io.IOException;
import java.util.List;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexInformation;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/25
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ESTest {

  @Autowired
  RestHighLevelClient restHighLevelClient;


  @Autowired
  ElasticsearchRestTemplate elasticsearchRestTemplate;

  @Autowired
  BlogRepository blogRepository;


  @Autowired
  ElasticsearchOperations elasticsearchOperations;

  /**
   * 索引测试
   */
  @Test
  public void index() throws IOException {
    IndexOperations indexOps = elasticsearchRestTemplate.indexOps(BlogModel.class);
    List<IndexInformation> information = indexOps.getInformation();
    Console.log("blog索引信息：{}", JSON.toJSONString(information, true));
  }

  /**
   * 文档测试
   */
  @Test
  public void document() {
    BlogModel blogModel = elasticsearchOperations.get("VLWMv3wBtR2-pZyPH4uB", BlogModel.class);
    Console.log("根据id查询：{}", blogModel);

    BlogModel model = new BlogModel()
        .setId("1")
        .setTitle("前后就饿看航空机枪和接口")
        .setContent("大苏打撒旦")
        .setUrl("请问请问");
    BlogModel save = elasticsearchOperations.save(model);
    Console.log("新增blog:{}", save);
  }


  @Test
  public void search() {
    //todo Spring Data Elasticsearch 提供了三种实现：CriteriaQuery,StringQuery和NativeSearchQuery.

    Criteria criteria = new Criteria("author").is("张三");
    Query criteriaQuery = new CriteriaQuery(criteria);
    SearchHits<BlogModel> criteriaSearchHits = elasticsearchRestTemplate.search(criteriaQuery,
        BlogModel.class);
    Console.log("criteria query hits :{}", criteriaSearchHits);

    Query stringQuery = new StringQuery("{ \"match\": { \"title\": { \"query\": \"ja\" } } } ");
    SearchHits<BlogModel> stringQuerySearchHits = elasticsearchOperations.search(stringQuery,
        BlogModel.class);
    Console.log("stringQuerySearchHits:{}", stringQuerySearchHits);

    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withQuery(QueryBuilders.termQuery("title", "java"))
        .build();

    SearchHits<BlogModel> blogModelSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery,
        BlogModel.class);
    Console.log("nativeSearchQueryHits:{}", blogModelSearchHits);

  }

}

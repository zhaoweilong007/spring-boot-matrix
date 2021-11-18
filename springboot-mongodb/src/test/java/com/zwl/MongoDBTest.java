package com.zwl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import com.zwl.dao.AnswerDao;
import com.zwl.entity.Answer;
import com.zwl.entity.AnswerEntity;
import com.zwl.repository.AnswerRepository;
import com.zwl.service.TransactionExample;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.beans.Transient;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDBApp.class)
@Slf4j
public class MongoDBTest {

  @Autowired AnswerRepository answerRepository;

  @Autowired MongoTemplate mongoTemplate;

  @Autowired MongoClient mongoClient;

  @Autowired AnswerDao answerDao;

  @Autowired TransactionExample transactionExample;

  @Test
  public void test() {
    List<Answer> all = answerRepository.findAll();
    log.info("all:{}", all);

    List<Answer> answers = mongoTemplate.findAll(Answer.class);
    log.info("answers:{}", answers);

    MongoDatabase demo = mongoClient.getDatabase("demo");
    ListCollectionsIterable<Document> documents = demo.listCollections();
    ArrayList<Document> docs = Lists.newArrayList(documents.iterator());
    log.info("demo database all collections:{}", docs);

    MongoCollection<Document> demoCollection = demo.getCollection("answer");
    FindIterable<Document> iterable = demoCollection.find();
    ArrayList<Document> list = new ArrayList<>();
    iterable.forEach(list::add);
    log.info("client query answer document:{}", list);

    ListDatabasesIterable<Document> databases = mongoClient.listDatabases();
    log.info("databases:{}", Lists.newArrayList(databases.iterator()));
  }

  @Test
  public void crud() {
    log.info("answer count:{}", answerRepository.count());
    Iterable<AnswerEntity> all = answerDao.findAll();
    List<Answer> answers =
        Lists.newArrayList(all).stream()
            .map(answerEntity -> BeanUtil.copyProperties(answerEntity, Answer.class))
            .collect(Collectors.toList());

    List<Answer> answerList = answerRepository.saveAll(answers);
    log.info("save answerList:{}", answerList);
    log.info("answer count:{}", answerRepository.count());

    List<Answer> anonymous = answerRepository.findAnswersByAuthorName("匿名用户");

    log.info("anonymous：{}", JSON.toJSONString(anonymous, true));

    List<Answer> list = answerRepository.findByQuest("如何看待");
    list.forEach(answer -> log.info(answer.getQuestion()));
  }

  @Test
  public void query() {
    Criteria criteria = new Criteria("author").is("zhangsan");
    Query query = new Query();
    query.addCriteria(criteria);
    Answer answer = mongoTemplate.findOne(query, Answer.class, "answer");
    log.info("answer:{}", answer);
  }

  @Test
  public void index() {
    String index =
        mongoTemplate
            .getCollection("answer")
            .createIndex(
                Indexes.descending("createDate"), new IndexOptions().name("createDate_index"));
    log.info("index:{}", index);
    String question_index =
        mongoTemplate
            .getCollection("answer")
            .createIndex(Indexes.text("question"), new IndexOptions().name("question_index"));
    log.info("question_index:{}", question_index);
    Set<String> collectionNames = mongoTemplate.getCollectionNames();
    log.info("collectionName:{}", collectionNames);
  }

  @Test
  public void aggregation() {
    GroupOperation operation = Aggregation.group("authorName").count().as("count");
    Aggregation aggregation = Aggregation.newAggregation(operation);
    AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "answer", Map.class);
    results.getMappedResults().forEach(map -> log.info("map:{}", map));

    SortOperation sortOperation = Aggregation.sort(Sort.by("createDate").descending());
    MatchOperation match = Aggregation.match(Criteria.where("authorName").is("匿名用户"));
    Aggregation newAggregation = Aggregation.newAggregation(match, sortOperation);
    AggregationResults<Map> aggregate =
        mongoTemplate.aggregate(newAggregation, "answer", Map.class);
    aggregate.getMappedResults().forEach(map -> log.info("map:{}", map));
  }

  @Test
  public void transactionTest() {
    Object object = transactionExample.insert();
    log.info("objects:{}", object);
  }

  // todo mongodb 在更新时会讲设置为空的字段也更新，通过update条件可过滤null字段
  @Test
  public void updateNonNull() {
    log.info("answer:{}", answerRepository.findById("1"));

    Update update = new Update();
    Answer answer = new Answer();
    answer.setId("1");
    answer.setTopicId(null);
    answer.setAnswerId(null);
    answer.setQuestionId(null);
    answer.setAuthorName("zhaoweilong");
    answer.setCreateDate(new Date());

    ReflectionUtils.doWithFields(
        Answer.class,
        field -> {
          if (field.getName().equals("id")
              || field.getAnnotation(Transient.class) != null
              || Modifier.isStatic(field.getModifiers())) {
            return;
          }
          field.setAccessible(true);
          if (field.get(answer) == null) {
            return;
          }
          update.set(field.getName(), field.get(answer));
        });

    UpdateResult updateFirst =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("_id").is(answer.getId())), update, Answer.class);
    log.info("updateFirst result:{}", updateFirst);

    log.info("answer:{}", answerRepository.findById("1"));
  }
}

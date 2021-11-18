package com.zwl.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zwl.model.BlogModel;
import com.zwl.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/26
 */
@RestController
@Slf4j
@RequestMapping("/blog")
public class BlogController {

  @Autowired BlogRepository blogRepository;

  @Autowired ElasticsearchRestTemplate elasticsearchRestTemplate;

  @GetMapping("queryPage")
  public ResponseEntity<Object> queryPage(
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", required = false, defaultValue = "11") Integer pageNum) {
    Iterable<BlogModel> iterable = blogRepository.findAll(PageRequest.of(pageNum, pageSize));
    return ResponseEntity.ok(iterable);
  }

  @GetMapping("queryAll")
  public ResponseEntity<Object> queryAll() {
    Iterable<BlogModel> iterable = blogRepository.findAll();
    ArrayList<BlogModel> blogModels = Lists.newArrayList();
    iterable.forEach(blogModels::add);
    return ResponseEntity.ok(blogModels);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> query(@PathVariable("id") String id) {
    Optional<BlogModel> optional = blogRepository.findById(id);
    return optional
        .<ResponseEntity<Object>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(500).build());
  }

  @PostMapping
  public ResponseEntity<Object> add(@RequestBody BlogModel blogModel) {
    return ResponseEntity.ok(blogRepository.save(blogModel));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") String id) {
    blogRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Object> update(@RequestBody BlogModel blogModel) {
    if (blogRepository.findById(blogModel.getId()).isEmpty()) {
      return ResponseEntity.ok().body("id不存在");
    }
    return ResponseEntity.ok(blogRepository.save(blogModel));
  }

  @GetMapping("findBlogModelByTitleLike")
  public ResponseEntity<Object> findBlogModelByTitleLike(String keyword) {
    List<BlogModel> list = blogRepository.findBlogModelByTitleLike(keyword);
    return ResponseEntity.ok(list);
  }

  /**
   * 全文搜索
   *
   * @param keyword 关键字
   * @param pageSize 每页大小
   * @param pageNum 当前页
   * @return 命中记录
   */
  @GetMapping("full")
  public ResponseEntity<Object> full(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
    PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
    NativeSearchQueryBuilder searchQueryBuilder =
        new NativeSearchQueryBuilder().withPageable(pageRequest);
    if (StrUtil.isNotBlank(keyword)) {
      searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keyword));
    }
    SearchHits<BlogModel> searchHits =
        elasticsearchRestTemplate.search(searchQueryBuilder.build(), BlogModel.class);
    return ResponseEntity.ok(searchHits);
  }

  /**
   * 多条件搜索
   *
   * @param blogModel 对象
   * @param pageSize 每页大小
   * @param pageNum 当前页
   * @return 命中记录
   */
  @GetMapping("search")
  public ResponseEntity<Object> search(
      BlogModel blogModel,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @RequestParam(value = "pageNum", required = false, defaultValue = "11") Integer pageNum) {
    PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
    NativeSearchQueryBuilder searchQueryBuilder =
        new NativeSearchQueryBuilder().withPageable(pageRequest);

    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
    if (StrUtil.isNotBlank(blogModel.getTitle())) {
      boolQueryBuilder.must(QueryBuilders.matchQuery("title", blogModel.getTitle()));
    }
    if (StrUtil.isNotBlank(blogModel.getAuthor())) {
      boolQueryBuilder.must(QueryBuilders.matchQuery("author", blogModel.getAuthor()));
    }
    if (StrUtil.isNotBlank(blogModel.getContent())) {
      boolQueryBuilder.should(QueryBuilders.matchQuery("content", blogModel.getContent()));
    }

    SearchHits<BlogModel> searchHits =
        elasticsearchRestTemplate.search(
            searchQueryBuilder.withQuery(boolQueryBuilder).build(), BlogModel.class);
    return ResponseEntity.ok(searchHits);
  }
}

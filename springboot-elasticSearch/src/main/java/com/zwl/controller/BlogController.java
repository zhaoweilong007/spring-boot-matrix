package com.zwl.controller;

import com.zwl.model.BlogModel;
import com.zwl.repository.BlogRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/26
 **/
@RestController
@Slf4j
@RequestMapping("/blog")
public class BlogController {

  @Autowired
  BlogRepository blogRepository;

  @GetMapping("{id}")
  public ResponseEntity<Object> query(@PathVariable("id") String id) {

    Optional<BlogModel> optional = blogRepository.findById(id);

    return optional.isPresent() ? ResponseEntity.ok().build()
        : ResponseEntity.status(500).build();
  }

  @PostMapping
  public ResponseEntity<Object> add(@RequestBody BlogModel blogModel) {
    return ResponseEntity.ok(blogRepository.save(blogModel));
  }

}

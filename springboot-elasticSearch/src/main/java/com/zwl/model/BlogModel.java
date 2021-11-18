package com.zwl.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/26
 */
@Data
@Accessors(chain = true)
@Document(indexName = "blog")
public class BlogModel implements Serializable {

  @Id private String id;

  private String title;

  private String author;

  private String content;

  private String url;
}

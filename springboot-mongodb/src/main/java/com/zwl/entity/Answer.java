package com.zwl.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 **/
@Data
@Document(collection = "answer")
public class Answer {

  private String id;

  private String title;

  private String url;

  private String content;

  private String author;

}

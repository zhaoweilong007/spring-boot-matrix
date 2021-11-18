package com.zwl.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/28
 */
@Data
@Document(collection = "answer")
@Accessors(chain = true)
public class Answer {

  @Id private String id;

  /** 话题id */
  private Integer topicId;

  /** 回答id */
  private Long answerId;

  /** 问题id */
  private Long questionId;

  /** 回答url */
  private String answerUrl;

  /** 问题 */
  private String question;

  /** 点赞数 */
  private Integer voteupCount;

  /** 回答片段 */
  private String excerpt;

  /** 作者名称 */
  private String authorName;

  /** 创建时间 */
  private Date createDate;

  /** 是否是神回复 0否 1是 */
  private Boolean isGodReplies;
}

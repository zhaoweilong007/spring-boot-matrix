package com.zwl.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 **/
@Entity
@Table(name = "answer")
@Data
public class AnswerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jdbc")
  private Long id;

  /**
   * 话题id
   */
  private Integer topicId;

  /**
   * 回答id
   */
  private Long answerId;

  /**
   * 问题id
   */
  private Long questionId;

  /**
   * 回答url
   */
  private String answerUrl;

  /**
   * 问题
   */
  private String question;

  /**
   * 点赞数
   */
  private Integer voteupCount;

  /**
   * 回答片段
   */
  private String excerpt;

  /**
   * 作者名称
   */
  private String authorName;

  /**
   * 创建时间
   */
  private Date createDate;


  /**
   * 是否是神回复 0否 1是
   */
  private Boolean isGodReplies;

  /**
   * 回答内容
   */
  private String content;

}

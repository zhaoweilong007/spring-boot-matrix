package com.zwl.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {

  @TableId private Long id;

  /** 话题id */
  @JSONField(alternateNames = {"topic_id"})
  private Integer topicId;

  /** 回答id */
  @JSONField(alternateNames = {"answer_id"})
  private Long answerId;

  /** 问题id */
  @JSONField(alternateNames = {"question_id"})
  private Long questionId;

  /** 回答url */
  @JSONField(alternateNames = {"answer_url"})
  private String answerUrl;

  /** 问题 */
  private String question;

  /** 点赞数 */
  @JSONField(alternateNames = {"voteup_count"})
  private Integer voteupCount;

  /** 回答片段 */
  private String excerpt;

  /** 作者名称 */
  @JSONField(alternateNames = {"author_name"})
  private String authorName;

  /** 创建时间 */
  @JSONField(alternateNames = {"create_date"})
  private Date createDate;

  /** 是否是神回复 0否 1是 */
  @JSONField(alternateNames = {"is_god_replies"})
  private Boolean isGodReplies;

  /** 回答内容 */
  private String content;
}

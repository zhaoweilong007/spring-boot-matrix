package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 */
@Entity
@Table(name = "answer")
@Data
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jdbc")
    private Integer id;

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
    private String title;

    /**
     * 点赞数
     */
    private Integer voteupCount;

    private Integer commentCount;

    /**
     * 作者名称
     */
    private String authorName;

}

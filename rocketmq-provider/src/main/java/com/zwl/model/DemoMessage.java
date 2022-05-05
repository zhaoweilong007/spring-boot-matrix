package com.zwl.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Data
public class DemoMessage implements Serializable {

  private Long id;

  private String desc;
}

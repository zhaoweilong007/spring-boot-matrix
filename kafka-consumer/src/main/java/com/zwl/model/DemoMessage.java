package com.zwl.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 **/
@Data
public class DemoMessage implements Serializable {

  private Long id;

  private String desc;
}

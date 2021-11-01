package com.zwl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户会话
 *
 * @author ZhaoWeiLong
 * @since 2021/8/18
 **/
@Data
@AllArgsConstructor
public class Session {

  private String userId;

  private String userName;

}

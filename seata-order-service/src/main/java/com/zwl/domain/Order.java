package com.zwl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName t_order
 */
@TableName(value = "t_order")
@Data
public class Order implements Serializable {
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
  /** */
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** */
  private Integer userId;
  /** */
  private Integer storageId;
  /** */
  private Double payAmount;
  /** */
  private Date updateTime;
  /** */
  private Date createTime;
}

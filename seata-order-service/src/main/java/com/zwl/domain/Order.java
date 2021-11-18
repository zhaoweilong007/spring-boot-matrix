package com.zwl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** @TableName order */
@TableName(value = "order")
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

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    Order other = (Order) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getUserId() == null
            ? other.getUserId() == null
            : this.getUserId().equals(other.getUserId()))
        && (this.getStorageId() == null
            ? other.getStorageId() == null
            : this.getStorageId().equals(other.getStorageId()))
        && (this.getPayAmount() == null
            ? other.getPayAmount() == null
            : this.getPayAmount().equals(other.getPayAmount()))
        && (this.getUpdateTime() == null
            ? other.getUpdateTime() == null
            : this.getUpdateTime().equals(other.getUpdateTime()))
        && (this.getCreateTime() == null
            ? other.getCreateTime() == null
            : this.getCreateTime().equals(other.getCreateTime()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
    result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
    result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
    result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", storageId=").append(storageId);
    sb.append(", payAmount=").append(payAmount);
    sb.append(", updateTime=").append(updateTime);
    sb.append(", createTime=").append(createTime);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}

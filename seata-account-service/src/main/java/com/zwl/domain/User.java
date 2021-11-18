package com.zwl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** @TableName user */
@TableName(value = "user")
@Data
public class User implements Serializable {
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
  /** */
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** */
  private String userName;
  /** */
  private Double amount;
  /** */
  private Date createTime;
  /** */
  private Date updateTime;

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
    User other = (User) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getUserName() == null
            ? other.getUserName() == null
            : this.getUserName().equals(other.getUserName()))
        && (this.getAmount() == null
            ? other.getAmount() == null
            : this.getAmount().equals(other.getAmount()))
        && (this.getCreateTime() == null
            ? other.getCreateTime() == null
            : this.getCreateTime().equals(other.getCreateTime()))
        && (this.getUpdateTime() == null
            ? other.getUpdateTime() == null
            : this.getUpdateTime().equals(other.getUpdateTime()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", userName=").append(userName);
    sb.append(", amount=").append(amount);
    sb.append(", createTime=").append(createTime);
    sb.append(", updateTime=").append(updateTime);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}

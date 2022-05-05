package com.zwl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName storage
 */
@TableName(value = "storage")
@Data
public class Storage implements Serializable {
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
  /** */
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** */
  private Integer stock;
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
    Storage other = (Storage) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getStock() == null
            ? other.getStock() == null
            : this.getStock().equals(other.getStock()))
        && (this.getUpdateTime() == null
            ? other.getUpdateTime() == null
            : this.getUpdateTime().equals(other.getUpdateTime()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getStock() == null) ? 0 : getStock().hashCode());
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
    sb.append(", stock=").append(stock);
    sb.append(", updateTime=").append(updateTime);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}

package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/11 14:08
 **/
@Entity
@Table(name = "record")
@Data
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jdbc")
    private Long id;

    private String label;

    private String name;

    private Integer sort;
}

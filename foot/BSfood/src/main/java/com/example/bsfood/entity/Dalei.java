package com.example.bsfood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dalei
 */
@TableName(value ="dalei")
@Data
public class Dalei implements Serializable {
    /**
     * 大类id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别名称
     */
    @TableField(value = "name")
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
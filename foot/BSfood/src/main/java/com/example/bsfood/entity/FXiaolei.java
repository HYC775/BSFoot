package com.example.bsfood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName f_xiaolei
 */
@TableName(value ="f_xiaolei")
@Data
public class FXiaolei implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "fid")
    private Integer fid;

    /**
     * 
     */
    @TableField(value = "xiaolei_id")
    private Integer xiaoleiId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
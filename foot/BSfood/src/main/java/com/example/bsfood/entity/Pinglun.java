package com.example.bsfood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName pinglun
 */
@TableName(value ="pinglun")
@Data
public class Pinglun implements Serializable {
    /**
     * id
     */
    @TableField(value = "id")
    private Integer id;

    @TableField(value = "fid")
    private Integer fid;

    /**
     * 评论内容
     */
    @TableField(value = "neirong")
    private String neirong;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 用户头像
     */
    @TableField(value = "userimg")
    private String userimg;

    /**
     * 父id
     */
    @TableField(value = "parentid")
    private Integer parentid;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 评论时间
     */
    @TableField(value = "createDate")
    private Date createdate;


    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<Pinglun> children = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
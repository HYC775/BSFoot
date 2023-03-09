package com.example.bsfood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName foot
 */
@TableName(value ="foot")
@Data
public class Foot implements Serializable {
    /**
     * 美食id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField(value = "footname")
    private String footname;

    /**
     * 美食内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 图片
     */
    @TableField(value = "imgs")
    private String imgs;

    /**
     * 发布人id
     */
    @TableField(value = "userid")
    private Integer userid;

    /**
     * 上传人
     */
    @TableField(value = "userName")
    private String username;

    /**
     * 发布时间
     */
    @TableField(value = "datatime")
    private String datatime;

    /**
     * 材料列表
     */
    @TableField(value = "cailiao")
    private String cailiao;

    /**
     * 制作步骤
     */
    @TableField(value = "zhizuofangfa")
    private String zhizuofangfa;

    /**
     * 大类id
     */
    @TableField(value = "dalei_id")
    private int daleiId;

    /**
     * 审核状态
     */
    @TableField(value = "state")
    private String state;

    /**
     * 唯一标识
     */
    @TableField(value = "guid")
    private String guid;

    @TableField(exist = false)
    private Dalei dalei;

    @TableField(exist = false)
    private List<FXiaolei> fxiaoleiList;

    @TableField(exist = false)
    private List<FXiaolei> fxiaoleiList0;

    @TableField(exist = false)
    private int[] xiaoleiids;

    @TableField(exist = false)
    private String xiaoleString;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
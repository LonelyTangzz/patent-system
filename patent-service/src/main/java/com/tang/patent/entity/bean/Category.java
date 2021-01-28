package com.tang.patent.entity.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("category")
public class Category {

    @TableId("pkId")
    private Long pkId;

    @TableField("categoryName")
    private String categoryName;

    @TableField("flag")
    private Boolean flag;
}
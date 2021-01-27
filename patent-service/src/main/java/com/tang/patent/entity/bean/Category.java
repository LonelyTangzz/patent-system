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

    @TableId("id")
    private Long id;

    @TableField("category")
    private String category;

    @TableField("flag")
    private Boolean flag;
}
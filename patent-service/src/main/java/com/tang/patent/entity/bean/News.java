package com.tang.patent.entity.bean;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository
public class News {

    private Long pkId;

    private String title;

    private String author;

    private String details;

    private Date updateTime;

    private String img;

}
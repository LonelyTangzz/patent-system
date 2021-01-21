package com.tang.patent.entity.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Patent {
    private Integer id;

    private String patentNo;

    private String patentName;

    private String category;

    private String location;

    private Double price;

    private String owner;

    private String details;

    private String img;

    private Date updatetime;
}
package com.tang.patent.bean;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo == null ? null : patentNo.trim();
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName == null ? null : patentName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "Patent{" +
                "id=" + id +
                ", patentNo='" + patentNo + '\'' +
                ", patentName='" + patentName + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", owner='" + owner + '\'' +
                ", details='" + details + '\'' +
                ", img='" + img + '\'' +
                ", updatetime=" + updatetime +
                '}';
    }
}
package com.example.patent.service;

import com.example.patent.bean.Category;
import com.example.patent.bean.Patent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface PatentService {

    List<Patent> selectPatentByName(String name);

    Boolean insertPatent(Patent patent);

    Boolean deleteById(Integer id);

    Boolean updateImg(Patent patent);

    Boolean updatePatent(Patent patent);

    List<Patent> getPatentByPage(Integer page);

    int countPatent();

    List<Map<String, Object>> countPatentOrderByCategory();

    HashMap<String,List<Patent>> getPatentGroupByCategory(List<Category> categories);
}

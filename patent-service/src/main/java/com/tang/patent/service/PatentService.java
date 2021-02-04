package com.tang.patent.service;

import com.tang.patent.entity.bean.Category;
import com.tang.patent.entity.bean.Patent;
import com.tang.vos.category.CategoryVo;
import com.tang.vos.patent.PatentVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PatentService {

    List<Patent> selectPatentByName(String name);

    Boolean insertPatent(Patent patent);

    Boolean deleteById(Integer id);

    Boolean updateImg(Patent patent);

    Boolean updatePatent(Patent patent);

    List<PatentVo> getPatentByPage(Integer page);

    int countPatent();

    List<Map<String, Object>> countPatentOrderByCategory();

    HashMap<String,List<Patent>> getPatentGroupByCategory(List<CategoryVo> categories);

    Patent getPatentById(Integer id);

    List<Patent> getPatentByCategory(String category, Integer page);
}

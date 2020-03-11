package com.example.patent.service;

import com.example.patent.bean.Patent;

import java.util.List;


public interface PatentService {

    List<Patent> selectPatentByName(String name);

    Boolean insertPatent(Patent patent);

    Boolean deleteByName(String name);

    Boolean updateImg(Patent patent);

    Boolean updatePatent(Patent patent);

    List<Patent> getAllPatent();

    int countPatent();
}

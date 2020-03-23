package com.example.patent.service.impl;

import com.example.patent.bean.Patent;
import com.example.patent.dao.PatentMapper;
import com.example.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatentServiceImpl implements PatentService {
    @Autowired
    private PatentMapper patentMapper;

    /**
     * 根据专利名模糊查询专利
     *
     * @param name
     * @return
     */
    @Override
    public List<Patent> selectPatentByName(String name) {
        return patentMapper.selectByName(name);
    }

    /**
     * 添加专利
     *
     * @param patent
     * @return
     */
    @Override
    public Boolean insertPatent(Patent patent) {
        return patentMapper.insert(patent) > 0 ? true : false;
    }

    /**
     * 删除专利
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(Integer id) {
        return patentMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    /**
     * 更新专利图片
     *
     * @param patent
     * @return
     */
    @Override
    public Boolean updateImg(Patent patent) {
        return patentMapper.updateUserImg(patent) > 0 ? true : false;
    }

    /**
     * 更新专利信息
     *
     * @param patent
     * @return
     */
    @Override
    public Boolean updatePatent(Patent patent) {
        return patentMapper.updateByNo(patent) > 0 ? true : false;
    }

    /**
     * 显示所有专利
     * @return
     */
    @Override
    public List<Patent> getPatentByPage(Integer page) {
        return patentMapper.getPatentByPage(page);
    }

    @Override
    public int countPatent() {
        return patentMapper.countPatent();
    }
}

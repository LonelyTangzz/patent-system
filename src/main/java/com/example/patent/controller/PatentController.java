package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.bean.Patent;
import com.example.patent.service.CategoryService;
import com.example.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class PatentController {
    @Autowired
    private PatentService patentService;
    @Autowired
    private CategoryService categoryService;

    Patent patent = new Patent();

    /**
     * 上传专利--未调试
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "patent_add.action", method = RequestMethod.POST)
    public Object patent_add(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Patent patent = new Patent();
        patent.setPatentNo(req.getParameter("patent_no"));
        patent.setPatentName(req.getParameter("patent_name"));
        int category = Integer.parseInt(req.getParameter("category"));
        String categoryName = categoryService.findCategory(category);
        patent.setCategory(categoryName);
        patent.setLocation(req.getParameter("location"));
        //patent.setOwner();
        patent.setPrice(Double.parseDouble(req.getParameter("price")));
        patent.setDetails(req.getParameter("details"));
        boolean res = patentService.insertPatent(patent);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "上传成功!");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败!");
            return jsonObject;
        }
    }

    /**
     * 上传专利图片
     * -------已调试
     *
     * @param file
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateImg.action", method = RequestMethod.POST)
    public Object updateImg(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "patentImg";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/patentImg/" + fileName;
        try {
            file.transferTo(dest);
            Patent patent = new Patent();
            patent.setId(Integer.parseInt(id));
            patent.setImg(storeAvatorPath);
            boolean res = patentService.updateImg(patent);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("avatar", storeAvatorPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传异常" + e.getMessage());
            return jsonObject;
        }
    }

    /**
     * 查询所有专利
     * -------未测试
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPatentList.action", method = RequestMethod.POST)
    public Object getPatentList() {
        JSONObject jsonObject = new JSONObject();
        if (patentService.getAllPatent().size() != 0) {
            jsonObject.put("allPatent", patentService.getAllPatent());
            jsonObject.put("code",1);
            return jsonObject;
        }else {
            jsonObject.put("code",0);
            jsonObject.put("msg","数据库连接出错");
            return  jsonObject;
        }
    }

    /**
     * 查询专利信息--调试了一半
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "search.action", method = RequestMethod.POST)
    public Object searchByName(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String searchName = req.getParameter("search_name");
        List<Patent> patent = patentService.selectPatentByName(searchName);
        jsonObject.put("patent", patent);
        if (patent.size() != 0) {
            return jsonObject;
        } else {
            jsonObject.put("msg", "没有找到与此相关的结果");
            return jsonObject;
        }
    }

    /**
     * 删除专利信息--未调试
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete.action", method = RequestMethod.POST)
    public Object deleteByName(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String searchName = req.getParameter("search_name");
        boolean res = patentService.deleteByName(searchName);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功!");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除失败!");
            return jsonObject;
        }
    }

    /**
     * 修改专利信息----未调试
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update.action", method = RequestMethod.POST)
    public Object updateInfo(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        patent.setId(Integer.parseInt(req.getParameter("id_patent")));
        patent.setCategory(categoryService.findCategory(Integer.parseInt(req.getParameter("category"))));
        patent.setOwner(req.getParameter("owner"));
        patent.setPrice(Double.parseDouble(req.getParameter("price")));
        patent.setLocation(req.getParameter("location"));
        patent.setDetails(req.getParameter("details"));
        boolean res = patentService.updatePatent(patent);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功!");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除失败!");
            return jsonObject;
        }
    }

    /**
     * 配置
     */
    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/patentImg/**").addResourceLocations("file:E:/IntellJ Idea/patent-system/patentImg/");
        }
    }
}

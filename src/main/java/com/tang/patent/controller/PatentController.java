package com.tang.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.patent.bean.Patent;
import com.tang.patent.service.CategoryService;
import com.tang.patent.service.NewsService;
import com.tang.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class PatentController {
    @Autowired
    private PatentService patentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NewsService newsService;

    Patent patent = new Patent();

    /**
     * 上传专利
     * ---测试通过
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "patentAdd.action", method = RequestMethod.POST)
    public Object patent_add(HttpServletRequest req) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        Patent patent = new Patent();
        patent.setPatentNo(req.getParameter("patent_no"));
        patent.setPatentName(req.getParameter("patent_name"));
        int category = Integer.parseInt(req.getParameter("category"));
        String categoryName = categoryService.findCategory(category);
        patent.setCategory(categoryName);
        patent.setLocation(req.getParameter("location"));
        patent.setOwner(req.getParameter("owner"));
        patent.setPrice(Double.parseDouble(req.getParameter("price")));
        patent.setDetails(req.getParameter("details"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        patent.setUpdatetime(simpleDateFormat.parse(req.getParameter("updateTime")));
        boolean res = patentService.insertPatent(patent);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "上传成功!");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败，请检查是否符合规定!");
            return jsonObject;
        }
    }

    /**
     * 上传专利图片
     * -------未调试
     *
     * @param file
     * @param id
     * @return
     */
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
     * 分页查询所有专利
     * ------测试通过
     *
     * @return
     */
    @RequestMapping(value = "getPatentByPage.action", method = RequestMethod.GET)
    public Object getPatentList(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", (int) Math.ceil((double) patentService.countPatent() / 15));
        jsonObject.put("patentList", patentService.getPatentByPage((Integer.parseInt(req.getParameter("page")) - 1) * 15));
        return jsonObject;

    }

    /**
     * 查询专利信息
     * ----测试通过
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "admin/searchPatent.action", method = RequestMethod.GET)
    public ModelAndView searchByName(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        String searchName = req.getParameter("searchName");
        List<Patent> patent = patentService.selectPatentByName(searchName);
        if (patent.size() != 0) {
            mv.addObject("code", 1);
            mv.addObject("patent", patent);
        } else {
            mv.addObject("code", 0);
        }
        mv.setViewName("admin/searchPage.html");
        return mv;
    }

    /**
     * 删除专利信息
     * ------测试通过
     * @param req
     * @return
     */
    @RequestMapping(value = "delePatent.action", method = RequestMethod.POST)
    public Object deleteByName(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Integer id = Integer.parseInt(req.getParameter("id"));
        boolean res = patentService.deleteById(id);
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
     * 修改专利信息
     * -----admin
     * ----调试通过
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "changePatent.action", method = RequestMethod.POST)
    public Object updateInfo(HttpServletRequest req) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        patent.setPatentNo(req.getParameter("patent_no"));
        patent.setPatentName(req.getParameter("patent_name"));
        //类别添加
        patent.setCategory(categoryService.findCategory(Integer.parseInt(req.getParameter("category"))));
        patent.setOwner(req.getParameter("owner"));
        patent.setPrice(Double.parseDouble(req.getParameter("price")));
        patent.setLocation(req.getParameter("location"));
        patent.setDetails(req.getParameter("details"));
        //时间转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        patent.setUpdatetime(simpleDateFormat.parse(req.getParameter("updateTime")));
        boolean res = patentService.updatePatent(patent);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "更新成功!");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "更新失败!");
            return jsonObject;
        }
    }

    /**
     * 返回专利种类以及该专利种类下专利数---admin
     * -------测试通过
     *
     * @return
     * @author: admin
     */
    @RequestMapping(value = "loadCategoryType.action", method = RequestMethod.GET)
    public Object loadCategoryType() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", patentService.countPatentOrderByCategory());
        return jsonObject;
    }

    /**
     * 单例专利详情
     * -----测试通过
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "patentDetails", method = RequestMethod.GET)
    public ModelAndView details(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        Integer id = Integer.parseInt(req.getParameter("id"));
        mv.setViewName("user/patentDetails.html");
        mv.addObject("patent", patentService.getPatentById(id));
        mv.addObject("allPatents", patentService.getPatentByPage(0));
        mv.addObject("totalNews", newsService.countNews());
        mv.addObject("totalPatents", patentService.countPatent());
        return mv;
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

    /**
     * 用户专利功能页面
     * ----测试通过
     *
     * @return
     */
    @RequestMapping(value = "patent")
    public ModelAndView patent(HttpServletRequest req) {
        int page;
        if (req.getParameter("page") == null)
            page = 0;
        else
            page = Integer.parseInt(req.getParameter("page"));
        ModelAndView mv = new ModelAndView("user/patent.html");
        mv.addObject("categories", patentService.countPatentOrderByCategory());
        mv.addObject("patents", patentService.getPatentByPage(page));
        return mv;
    }
    /**
     * 分行业显示部分专利
     * ----测试通过
     *
     * @return
     */
    @RequestMapping(value = "patentSort")
    public ModelAndView patentSort(HttpServletRequest req) {
        String category = req.getParameter("name");
        Integer page = Integer.parseInt(req.getParameter("page"));
        ModelAndView mv = new ModelAndView("user/patentSort.html");
        mv.addObject("patents",patentService.getPatentByCategory(category,page));
        mv.addObject("categories", patentService.countPatentOrderByCategory());
        return mv;
    }
}

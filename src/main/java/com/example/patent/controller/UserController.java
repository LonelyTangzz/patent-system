package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.bean.Category;
import com.example.patent.bean.Patent;
import com.example.patent.bean.User;
import com.example.patent.common.MD5;
import com.example.patent.service.CategoryService;
import com.example.patent.service.PatentService;
import com.example.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PatentService patentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    User user;

    MD5 md5 = new MD5();
    /**
     * 注册入口
     * ------测试通过
     *
     * @param user 用户
     * @return
     * @author: user
     */
    @ResponseBody
    @RequestMapping(value = "register.action", method = RequestMethod.POST)
    public Object register(User user) {
        JSONObject jsonObject = new JSONObject();
        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        boolean res = userService.addUser(user);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "注册成功，跳转中");
            jsonObject.put("name", user.getUsername());
            jsonObject.put("id", userService.getUserIdByName(user.getUsername()));
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败，请重试");
            return jsonObject;
        }
    }

    /**
     * 登录信息入口
     * ------测试通过
     *
     * @param req
     * @return
     * @author: user
     */
    @ResponseBody
    @RequestMapping(value = "login.action", method = RequestMethod.POST)
    public Object login(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        if (userService.checkAccount(req.getParameter("name"), req.getParameter("password"))) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功跳转中");
            jsonObject.put("name", req.getParameter("name"));
            jsonObject.put("id", userService.getUserIdByName(req.getParameter("name")));
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
    }

    /**
     * 用户修改密码
     * ---未测试
     *
     * @param req
     * @return
     * @author: user
     */
    @ResponseBody
    @RequestMapping("resetPassword.action")
    public Object resetPassword(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        //将localStorage里面的用户名取出放到form表单里一起提交过来

        boolean res = userService.checkAccount(req.getParameter("name"), req.getParameter("befo_pasword"));
        if (res) {
            userService.updatePassword(req.getParameter("name"), req.getParameter("now_password"));
            jsonObject.put("code", "1");
            return jsonObject;
        } else {
            jsonObject.put("code", "0");
            return jsonObject;
        }
    }


    /**
     * 信息修改入口
     * ------测试通过 tips:我认为这里的逻辑需要优化
     *
     * @param req
     * @return
     * @author: user
     */
    @ResponseBody
    @RequestMapping(value = "updateInfo.action", method = RequestMethod.POST)
    public Object updateInfo(HttpServletRequest req) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        user.setId(Integer.parseInt(req.getParameter("id")));
        if (req.getParameter("sex") != null)
            user.setSex(Byte.parseByte(req.getParameter("sex")));
        if (req.getParameter("birth") != "" && req.getParameter("birth") != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            user.setBirth(simpleDateFormat.parse(req.getParameter("birth")));
        }
        user.setEmail(req.getParameter("email"));
        user.setLocation(req.getParameter("location"));
        user.setPhoneNum(req.getParameter("phoneNum"));
        user.setRealname(req.getParameter("realname"));
        if (userService.updateUserInfo(user)) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功！");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败请检查字段是否符合规定！");
            return jsonObject;
        }
    }

    /**
     * 上传头像-------功能设计完毕
     * ------测试通过
     *
     * @return
     * @author: user
     */
    @ResponseBody
    @RequestMapping(value = "updateAvatar.action", method = RequestMethod.POST)
    public Object updateAvatar(@RequestParam("file") MultipartFile file, @RequestParam("user_id") String user_id, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件为空，请检查");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + file.getOriginalFilename() + user_id;
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatarPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String userAvatorPath = "/avatarPic/" + fileName;
        try {
            response.sendRedirect("/patent/views/admin/userControl.html");
            file.transferTo(dest);
            User user = new User();
            user.setId(Integer.parseInt(user_id));
            user.setAvatar(userAvatorPath);
            boolean res = userService.updateUserAvatar(user);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("avatar", userAvatorPath);
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
     * 管理员分页显示所有用户
     * ------测试通过
     *
     * @return
     * @author: admin
     */
    @ResponseBody
    @RequestMapping(value = "getPageUser.action", method = RequestMethod.GET)
    public Object getPageUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", (int) Math.ceil((double) userService.countUser() / 10));
        jsonObject.put("userList", userService.getPageUser((Integer.parseInt(req.getParameter("page")) - 1) * 10));
        return jsonObject;
    }

    /**
     * 根据当前年月份获取当月注册用户
     *
     * @param req
     * @return
     * @author: admin
     */
    @ResponseBody
    @RequestMapping(value = "getUserTimeByMonth.action", method = RequestMethod.GET)
    public Object getUserTimeByMonth(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> result = userService.countUserByMonth(req.getParameter("year"), req.getParameter("month"));
        jsonObject.put("result", result);
        return jsonObject;

    }

    /**
     * 配置
     */
    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/avatarPic/**").addResourceLocations("file:E:/IntellJ Idea/patent-system/avatarPic/");
        }
    }

    /**
     * 以下为页面配置
     */
    @RequestMapping(value = "index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index.html");
        mv.addObject("patents",patentService.getPatentByPage(0));
        return mv;
    }

    @RequestMapping(value = "login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("user/page-login.html");
        return mv;
    }

    @RequestMapping(value = "register")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("user/age-register.html");
        return mv;
    }
    @RequestMapping(value = "templates")
    public ModelAndView templates() {
        ModelAndView mv = new ModelAndView("templates.html");
        mv.addObject("categories",categoryService.getAllCategory());
        HashMap<String,List<Patent>> patents = patentService.getPatentGroupByCategory(categoryService.getAllCategory());
        mv.addObject("patents",patents);
        return mv;
    }
}

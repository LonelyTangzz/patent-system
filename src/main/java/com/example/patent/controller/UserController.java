package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.bean.User;
import com.example.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册入口
     * ------测试通过
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register.action", method = RequestMethod.POST)
    public Object register(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        boolean res = userService.addUser(req.getParameter("name"), req.getParameter("password"));
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "注册成功，跳转中");
            jsonObject.put("name", req.getParameter("name"));
            jsonObject.put("id", userService.getUserIdByName(req.getParameter("name")));
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
     * 信息修改入口-----已通过
     *------测试通过
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateInfo.action", method = RequestMethod.POST)
    public Object updateInfo(HttpServletRequest req) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("id")));
        user.setSex(Byte.parseByte(req.getParameter("sex")));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirth(simpleDateFormat.parse(req.getParameter("birth")));
        user.setEmail(req.getParameter("email"));
        user.setLocation(req.getParameter("location"));
        user.setPhoneNum(req.getParameter("phoneNum"));
        user.setRealname(req.getParameter("realname"));
        if (userService.updateUserInfo(user)) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功！");
            return jsonObject;
        } else {
            jsonObject.put("code",0);
            jsonObject.put("msg","修改失败请检查字段是否符合规定！");
            return jsonObject;
        }
    }

    /**
     * 上传头像-------功能设计完毕
     * ------测试通过
     *
     * @return
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
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
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
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPageUser.action", method = RequestMethod.POST)
    public Object getPageUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", (int) Math.ceil((double) userService.countUser() / 10));
        jsonObject.put("userList", userService.getPageUser((Integer.parseInt(req.getParameter("page")) - 1) * 10));
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
     * 测试页面
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object Atest(@RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败");
            return jsonObject;
        } else {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatarPic";
            System.out.println(fileName);
            System.out.println(filePath);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "传过来啦");
//        File file1 = new File(filePath);
//        if (!file1.exists()) {
//            file1.mkdir();
//        }
//        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
//        String storeAvatorPath = "/avatarPic/" + fileName;
            return jsonObject;
        }
    }
}

package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.example.patent.bean.Patent;
import com.example.patent.bean.User;
import com.example.patent.common.MD5;
import com.example.patent.common.SendSms;
import com.example.patent.service.CategoryService;
import com.example.patent.service.NewsService;
import com.example.patent.service.PatentService;
import com.example.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private NewsService newsService;
    @Autowired
    User user;

    MD5 md5 = new MD5();

    public static int newcode;

    /**
     * 注册入口
     * ------测试通过
     *
     * @param user 用户
     * @return
     * @author: user
     */
    @RequestMapping(value = "register.action", method = RequestMethod.POST)
    public ModelAndView register(User user, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        user.setPhoneNum(req.getParameter("phoneNum"));
        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        user.setAvatar("/avatarPic/img_avatar.png");
        if (req.getParameter("phoneNum") == "") {
            mv.setViewName("user/register.html");
            mv.addObject("msg", "请不要传空值！");
        } else if (Integer.parseInt(req.getParameter("verifyCode")) != newcode) {
            mv.setViewName("user/register.html");
            mv.addObject("msg", "验证码错误！");
        } else if (userService.addUser(user)) {
            mv.addObject("username", req.getParameter("name"));
            mv.addObject("msg", "注册成功！");
            mv.setViewName("user/login.html");
        } else {
            mv.setViewName("user/register.html");
            mv.addObject("msg", "注册出现异常，请重试！");
        }
        return mv;
    }

    /**
     * 登录信息入口
     * ------测试通过
     *
     * @param req
     * @return
     * @author: user
     */
    @RequestMapping(value = "login.action", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        if (userService.checkAccount(req.getParameter("username"), md5.getMD5ofStr(req.getParameter("password")))) {
            User user = new User();
            user.setId(userService.getUserIdByName(req.getParameter("username")));
            user.setLoginTime(new Date());
            userService.updateUserInfo(user);
            mv.setViewName("index.html");
            mv.addObject("username", req.getParameter("username"));
            mv.addObject("msg", "登录成功");
            mv.addObject("categories", categoryService.getAllCategory());
            mv.addObject("allPatents", patentService.getPatentByPage(0));
            mv.addObject("totalNews", newsService.countNews());
            mv.addObject("totalPatents", patentService.countPatent());
            mv.addObject("news", newsService.getNewsByPage(0));
        } else {
            mv.setViewName("user/login.html");
            mv.addObject("msg", "账号密码错误，请重试！");
        }
        return mv;
    }


    /**
     * 用户修改密码
     * ---测试通过
     * @return
     */
    @RequestMapping(value = "submitPassword.action", method = RequestMethod.POST)
    public Object submitPassword(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String befoPassword = req.getParameter("befoPassword");
        String nowPassword = req.getParameter("nowPassword");
        String username = req.getParameter("username");
        if (userService.checkAccount(username, md5.getMD5ofStr(befoPassword))) {
            userService.updatePassword(username, nowPassword);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "密码错误请重试！");
        }
        return jsonObject;
    }

    /**
     * 信息修改入口
     * ------测试通过 tips:管理员入口
     *
     * @param req
     * @return
     * @author: user
     */
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
     * 上传头像-------管理员通道
     * ------测试通过
     *
     * @return
     * @author: admin
     */
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
            response.sendRedirect("admin/userControl");
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
    @RequestMapping(value = "getPageUser.action", method = RequestMethod.GET)
    public Object getPageUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", (int) Math.ceil((double) userService.countUser() / 10));
        jsonObject.put("userList", userService.getPageUser((Integer.parseInt(req.getParameter("page")) - 1) * 10));
        return jsonObject;
    }

    /**
     * 根据当前年月份获取当月注册用户
     * ------测试通过
     *
     * @param req
     * @return
     * @author: admin
     */
    @RequestMapping(value = "getUserTimeByMonth.action", method = RequestMethod.GET)
    public Object getUserTimeByMonth(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> result = userService.countUserByMonth(req.getParameter("year"), req.getParameter("month"));
        jsonObject.put("result", result);
        return jsonObject;

    }

    /**
     * 注册绑定手机号时发送验证码
     * ----测试通过
     *
     * @param req
     * @throws ClientException
     */
    @RequestMapping(value = "verifyPhone", method = RequestMethod.POST)
    public boolean verifyPhone(HttpServletRequest req) throws ClientException {
        SendSms sendSms = new SendSms();
        String phoneNum = req.getParameter("phoneNum");
        //随机码生成
        newcode = (int) (Math.random() * 9999) + 100;
        return sendSms.register(phoneNum, Integer.toString(newcode));
    }

    /**
     * 忘记密码时第一个步骤
     * ----测试通过
     */
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
    public ModelAndView forgetPassword(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("user/verifyInfo.html");
        String username = req.getParameter("username");
        if (userService.getUserPhoneByName(username) == null) {
            mv.setViewName("user/resetPassword.html");
            mv.addObject("msg", "该用户名不存在");
        } else {
            String phoneNum = userService.getUserPhoneByName(username);
            mv.addObject("phoneNum", phoneNum);
            mv.addObject("username", username);
        }
        return mv;
    }

    /**
     * 忘记密码时第二个步骤
     * ---测试通过
     *
     * @param req
     * @return
     * @author: user
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public boolean resetPassword(HttpServletRequest req) throws ClientException {
        String phoneNum = req.getParameter("phoneNum");
        SendSms sendSms = new SendSms();
        //随机码生成
        newcode = (int) (Math.random() * 9999) + 100;
        return sendSms.forgetPassword(phoneNum, Integer.toString(newcode));
    }

    /**
     * 忘记密码时第三个步骤
     * ---测试通过
     *
     * @param req
     * @return
     * @author: user
     */
    @RequestMapping("changePassword")
    public ModelAndView changePassword(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        if (Integer.parseInt(req.getParameter("verifyCode")) == newcode) {
            if (userService.updatePassword(req.getParameter("username"), md5.getMD5ofStr(req.getParameter("password")))) {
                mv.setViewName("user/login.html");
                mv.addObject("msg", "重置成功");
            } else {
                mv.setViewName("user/verifyInfo.html");
                mv.addObject("msg", "修改出现异常");
            }
        } else {
            mv.setViewName("user/verifyInfo.html");
            mv.addObject("msg", "验证码错误");
        }
        return mv;
    }

    /**
     * 上传头像-------用户通道
     * ------测试通过
     *
     * @return
     * @author: user
     */
    @RequestMapping(value = "updateAvatar", method = RequestMethod.POST)
    public Object updateUserAvatar(@RequestParam("file") MultipartFile file, @RequestParam("user_id") String user_id, HttpServletResponse response) {
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
            response.sendRedirect("personInfo");
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

    @RequestMapping(value = "changeInfo", method = RequestMethod.POST)
    public void changeInfo(User user, HttpServletRequest req, HttpServletResponse resp, String birthChange) throws IOException, ParseException {
        if (birthChange != "" && birthChange != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            user.setBirth(simpleDateFormat.parse(birthChange));
        }
        user.setId(userService.getUserIdByName(user.getUsername()));
        boolean res = userService.updateUserInfo(user);
        if (res) {
            resp.sendRedirect("personInfo");
        } else {
            System.out.println("have A error In updating");
        }
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
        mv.addObject("categories", categoryService.getAllCategory());
        mv.addObject("allPatents", patentService.getPatentByPage(0));
        mv.addObject("totalNews", newsService.countNews());
        mv.addObject("totalPatents", patentService.countPatent());
        mv.addObject("news", newsService.getNewsByPage(0));
        return mv;
    }

    @RequestMapping(value = "login")
    public ModelAndView login() {
        return new ModelAndView("user/login.html");
    }

    @RequestMapping(value = "register")
    public ModelAndView register() {
        return new ModelAndView("user/register.html");
    }

    @RequestMapping(value = "templates")
    public ModelAndView templates() {
        ModelAndView mv = new ModelAndView("templates.html");
        mv.addObject("categories", categoryService.getAllCategory());
        HashMap<String, List<Patent>> patents = patentService.getPatentGroupByCategory(categoryService.getAllCategory());
        mv.addObject("patents", patents);
        return mv;
    }

    @RequestMapping(value = "resetPassword")
    public ModelAndView resetPassword() {
        return new ModelAndView("user/resetPassword.html");
    }

    @RequestMapping(value = "personInfo")
    public ModelAndView personInfo(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        Cookie cookie[] = req.getCookies();
        String username = new String();
        for (int i = 0; i < cookie.length; i++) {
            if (cookie[i].getName().equals("username"))
                username = cookie[i].getValue();
        }
        if (username.equals("")) {
            mv.setViewName("user/login.html");
        } else {
            mv.setViewName("user/personInfo.html");
            mv.addObject("userInfo", userService.getUserByName(username));
        }
        return mv;
    }

    @RequestMapping(value = "user_changePassword")
    public ModelAndView ChangePassword() {
        return new ModelAndView("user/changePassword.html");
    }

    @RequestMapping(value = "user_addPatent")
    public ModelAndView addPatent(HttpServletResponse resp) {
        return new ModelAndView("user/addPatent.html");
    }
}
package com.tang.patent.tools;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tang.patent.common.Constants;
import com.tang.patent.dao.SmsLogMapper;
import com.tang.patent.entity.bean.SmsLog;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author tangzy
 * @version 1.0
 * @name SendSms
 * @description: 短信验证码测试
 * @since 2021/1/16
 */
@Component
public class SendSms {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    SmsLogMapper smsLogMapper;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 个人账户许可及密钥
     */
    private static final String ACCESS_KEY_ID = "LTAI4G8eapip13RowK6MJcWo";
    private static final String ACCESS_KEY_SECRET = "SrEIysEtyudq7W3vuFRbi8JqARFLLG";
    /**
     * 对应的短信模版编号
     */
    private static final String FORGET_PASSWORD_TEMPLATE = "SMS_189018362";
    private static final String REGISTER_TEMPLATE = "SMS_188993563";

    private static final String ARRAYS = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 注册短信
     *
     * @param telephone 手机号
     * @return 操作结果
     * @throws ClientException 客户端异常
     */
    public Boolean register(String telephone) throws ClientException {
        return sendSmsTemplate(telephone, REGISTER_TEMPLATE, "REGISTER");
    }

    /**
     * 重置密码
     *
     * @param telephone 手机号
     * @return 操作结果
     * @throws ClientException 客户端异常
     */
    public Boolean forgetPassword(String telephone) throws ClientException {
        return sendSmsTemplate(telephone, FORGET_PASSWORD_TEMPLATE, "RESET");
    }

    /**
     * 发送短信，并将发送日志存入mysql日志及将短信code存入redis缓存
     *
     * @param telephone    手机号
     * @param templateCode 模版
     * @return 操作结果
     * @throws ClientException 短信发送异常
     */
    private Boolean sendSmsTemplate(String telephone, String templateCode, String action) throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //6位随机码生成
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += ARRAYS.charAt((int) (Math.random() * ARRAYS.length()));
        }
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(telephone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("专利资讯网");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            //存储验证码至redis缓存
            valueOperations.set(Constants.SMS_PREFIX.concat(telephone), code, 5, TimeUnit.MINUTES);
            SmsLog smsLog = new SmsLog(IdWorker.getId(), telephone, action, new Date());
            //记录操作到数据库
            smsLogMapper.insertMessageLog(smsLog);
            System.out.println("短信发送成功！");
            return true;
        } else {
            System.out.println("短信发送失败！");
            return false;
        }
    }
}

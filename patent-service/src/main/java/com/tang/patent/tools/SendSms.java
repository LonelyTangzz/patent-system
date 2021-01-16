package com.tang.patent.tools;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @name SendSms
 * @author tangzy
 * @since 2021/1/16
 * @version 1.0
 * @description: 短信验证码测试
 */
public class SendSms {

    /**
     *  产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    static final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 个人账户及secret
     */
    static final String accessKeyId = "LTAI4G8eapip13RowK6MJcWo";
    static final String accessKeySecret = "SrEIysEtyudq7W3vuFRbi8JqARFLLG";
    /**
     * 对应的短信模版编号
     */
    static final String FORGET_PASSWORD_TEMPLATE = "SMS_189018362";
    static final String REGISTER_TEMPLATE = "SMS_188993563";

    /**
     * 注册短信
     * @param telephone 手机号
     * @return 操作结果
     * @throws ClientException 客户端异常
     */
    public Integer register(String telephone) throws ClientException {
        return sendSmsTemplate(telephone, REGISTER_TEMPLATE);
    }

    public Integer forgetPassword(String telephone) throws ClientException {
        return sendSmsTemplate(telephone, FORGET_PASSWORD_TEMPLATE);
    }

    private Integer sendSmsTemplate(String telephone, String templateCode) throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //随机码生成
        int code = (int) (Math.random() * 9999) + 100;

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(telephone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("专利资讯网");
        // 必填:短信模板-可在短信控制台中找到
        //SMS_189018362为用户忘记密码Code
        //SMS_188993563为用户注册时Code
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getCode());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功！");
            return null;
        } else {
            System.out.println("短信发送失败！");
            return null;
        }
    }
}

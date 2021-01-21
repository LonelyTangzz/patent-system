package com.tang.patent.dao;

import com.tang.patent.entity.bean.SmsLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/20.
 * @name: SmsLogMapper
 * @description: 短信发送日志传输层
 */
@Mapper
public interface SmsLogMapper {

    /**
     * 插入短信发送日志
     *
     * @param smsLog 短信日志
     * @return 操作结果
     */
    Integer insertMessageLog(@Param("smsLog") SmsLog smsLog);

}

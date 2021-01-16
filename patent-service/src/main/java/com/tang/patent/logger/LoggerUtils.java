package com.tang.patent.logger;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/8.
 * @name: LoggerUtils
 * @description: 打印日志工具类
 */
@Data
public class LoggerUtils implements Serializable {

    private static Logger logger;

    public LoggerUtils(String pack) {
        logger = LoggerFactory.getLogger(pack);
    }

    public void startLog() {
        logger.info("开始任务");
    }

    public void endLog() {
        logger.info("结束任务");
    }

}

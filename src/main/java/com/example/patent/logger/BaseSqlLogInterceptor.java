package com.example.patent.logger;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/8.
 * @name: BaseSqlLogInterceptor
 * @description: 打印sql语句log
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class BaseSqlLogInterceptor implements Interceptor {
    protected static Logger logger = LoggerFactory.getLogger(BaseSqlLogInterceptor.class);

    public BaseSqlLogInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;
        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = end - start;
        if (time > 1L) {
            String sql = getSql(configuration, boundSql, sqlId, time);
            logger.warn(sql);
        }

        return returnValue;
    }

    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = (new StringBuilder(100)).append(sqlId).append(" : time = ").append(time).append("ms ").append(sql);
        return str.toString();
    }

    public static String makeQueryStringAllRegExp(String str) {
        return str != null && str != "" ? str.replace("\\", "\\\\").replace("*", "\\*").replace("+", "\\+").replace("|", "\\|").replace("{", "\\{").replace("}", "\\}").replace("(", "\\(").replace(")", "\\)").replace("^", "\\^").replace("$", "\\$").replace("[", "\\[").replace("]", "\\]").replace("?", "\\?").replace(",", "\\,").replace(".", "\\.").replace("&", "\\&") : str;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else if (obj != null) {
            value = obj.toString();
        } else {
            value = "";
        }

        return value != null ? makeQueryStringAllRegExp(value) : value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                Iterator var7 = parameterMappings.iterator();

                while (var7.hasNext()) {
                    ParameterMapping parameterMapping = (ParameterMapping) var7.next();
                    String propertyName = parameterMapping.getProperty();
                    Object obj;
                    if (metaObject.hasGetter(propertyName)) {
                        obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }

        return sql;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}

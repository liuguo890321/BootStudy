package com.asiainfo.util.pageUtil;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Properties;



/**
 * Created by lenovo on 2017/5/16.
 */
@Intercepts({@Signature(type =StatementHandler.class, method = "prepare", args ={Connection.class, Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
    private static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();
    private static final String defaultDialect = "mysql";
    private static final String defaultPageSqlId = ".*Page$";
    private String dialect = "";
    private String pageSqlId = "";

    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler =  SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            Configuration configuration = (Configuration) metaStatementHandler.
                    getValue("delegate.configuration");
            dialect = configuration.getVariables().getProperty("dialect");
            if (null == dialect || "".equals(dialect)) {
                logger.warn("Property dialect is not setted,use default 'mysql' ");
                dialect = defaultDialect;
            }
            pageSqlId = configuration.getVariables().getProperty("pageSqlId");
            if (null == pageSqlId || "".equals(pageSqlId)) {
                logger.warn("Property pageSqlId is not setted,use default '.*Page$' ");
                pageSqlId = defaultPageSqlId;
            }
            MappedStatement mappedStatement = (MappedStatement)
                    metaStatementHandler.getValue("delegate.mappedStatement");
            // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的
            //  MappedStatement的sql
            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject is null!");
                } else {
                    // 分页参数作为参数对象parameterObject的一个属性
                    Page page = (Page) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
                    localPage.set(page);
                    String sql = boundSql.getSql();
                    // 重写sql
                    String pageSql = buildPageSql(sql, page);
                    metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                    // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                    metaStatementHandler.setValue("delegate.rowBounds.offset",
                            RowBounds.NO_ROW_OFFSET);
                    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                    Connection connection = (Connection) invocation.getArgs()[0];
                    // 重设分页参数里的总页数等
                    setPageParameter(sql, connection, mappedStatement, boundSql, page);
                }
            }
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        }else if (invocation.getTarget() instanceof ResultSetHandler){
            try {
                Object result = invocation.proceed();
                Page page = localPage.get();
                if(page != null){
                    page.setResult((List) result);
                }
                return result;
            }finally {
                localPage.remove();
            }

        }
        return null;
    }

    private String buildPageSql(String sql, Page page) {
        if (page != null) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equals(dialect)) {
                pageSql = buildPageSqlForOracle(sql, page);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public StringBuilder buildPageSqlForMysql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }

    public StringBuilder buildPageSqlForOracle(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }

    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Page page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") as total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }


    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }


    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {

    }

}

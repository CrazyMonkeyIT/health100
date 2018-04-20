package com.valueservice.djs.db.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Description:<br />
 * </p>
 * @author Bill Chan
 * @date 2017年7月31日 上午12:03:39
 */
@Configuration
@MapperScan("com.valueservice.djs.db.dao")
public class DruidDataSourceConfig {

	@Resource
	DruidConfigProperties druidConfigProperties;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidConfigProperties.getMasterDriverClassName());
        druidDataSource.setUrl(druidConfigProperties.getMasterUrl());
        druidDataSource.setUsername(druidConfigProperties.getMasterUsername());
        druidDataSource.setPassword(druidConfigProperties.getMasterPassword());
        setConnectionPool(druidDataSource);
        return druidDataSource;
    }

	private void setConnectionPool(DruidDataSource druidDataSource)
			throws SQLException {
		druidDataSource.setInitialSize(druidConfigProperties.getMinIdle());
        druidDataSource.setMinIdle(druidConfigProperties.getMinIdle());
        druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
        druidDataSource.setMaxWait(druidConfigProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidConfigProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidConfigProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidConfigProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidConfigProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(druidConfigProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidConfigProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setFilters(druidConfigProperties.getFilters());
        druidDataSource.setConnectionProperties(druidConfigProperties.getConnectionProperties());
	}

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "bill");
        initParameters.put("loginPassword", "Bill%%123");
        initParameters.put("resetEnable", "false");
//        initParameters.put("allow", "X.X.X.X");
        // (存在共同时，deny优先于allow)
//        initParameters.put("deny", "X.X.X.X");
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions",
                "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean(value = "druid-stat-interceptor")
    public DruidStatInterceptor DruidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        // 监控如下两个controller的spring处理情况
        beanNameAutoProxyCreator.setBeanNames("loginController");
        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
        return beanNameAutoProxyCreator;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}

package org.jdonee.cooking.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.jdonee.cooking.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class DruidConfig {

	private static Logger log = LoggerFactory.getLogger(DruidConfig.class);
	/**
	 * 注册一个StatViewServlet
	 *
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidStatViewServlet(@Value("${druid.loginUsername}") String loginUsername,
			@Value("${druid.loginPassword}") String loginPassword, @Value("${druid.resetEnable}") String resetEnable) {
		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");
		// 添加初始化参数：initParams
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
		servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", resetEnable);
		return servletRegistrationBean;
	}

	/**
	 * 注册一个：filterRegistrationBean
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	@Bean
	public DataSource druidDataSource(@Value("${spring.datasource.driverClass}") String driver,
			@Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password,
			@Value("${spring.datasource.filters}") String filters,
			@Value("${spring.datasource.maxActive}") int maxActive,
			@Value("${spring.datasource.initialSize}") int initialSize,
			@Value("${spring.datasource.maxWait}") long maxWait, @Value("${spring.datasource.minIdle}") int minIdle,
			@Value("${spring.datasource.timeBetweenEvictionRunsMillis}") int timeBetweenEvictionRunsMillis,
			@Value("${spring.datasource.minEvictableIdleTimeMillis}") long minEvictableIdleTimeMillis,
			@Value("${spring.datasource.validationQuery}") String validationQuery,
			@Value("${spring.datasource.testWhileIdle}") boolean testWhileIdle,
			@Value("${spring.datasource.testOnBorrow}") boolean testOnBorrow,
			@Value("${spring.datasource.testOnReturn}") boolean testOnReturn,
			@Value("${spring.datasource.poolPreparedStatements}") boolean poolPreparedStatements,
			@Value("${spring.datasource.maxOpenPreparedStatements}") int maxOpenPreparedStatements,
			@Value("${spring.datasource.connectionProperties}") String connectionProperties,
									  @Value("${spring.datasource.dialect}") String dialect) {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		druidDataSource.setConnectionProperties(connectionProperties);
		druidDataSource.setDbType(dialect);
		try {
			druidDataSource.setFilters(filters);// 配置防火墙和统计插件
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		List<Filter> filteList = new ArrayList();
		filteList.add(getWallFilter());
		druidDataSource.setProxyFilters(filteList);

		return druidDataSource;


		/*List<Filter> filteList = new ArrayList();
		filteList.add(getWallFilter());

		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(settings.getUrl());
		druidDataSource.setUsername(settings.getUsername());
		druidDataSource.setPassword(settings.getPassword());
		druidDataSource.setInitialSize(Integer.parseInt(settings.getInitialSize()));
		druidDataSource.setMinIdle(Integer.parseInt(settings.getMinIdle()));
		druidDataSource.setMaxActive(Integer.parseInt(settings.getMaxActive()));
		druidDataSource.setMaxWait(Integer.parseInt(settings.getMaxWait()));
		druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(settings.getTimeBetweenEvictionRunsMillis()));
		druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(settings.getMinEvictableIdleTimeMillis()));
		druidDataSource.setValidationQuery(settings.getValidationQuery());
		druidDataSource.setTestWhileIdle(Boolean.parseBoolean(settings.getTestWhileIdle()));
		druidDataSource.setTestOnBorrow(Boolean.parseBoolean(settings.getTestOnBorrow()));
		druidDataSource.setTestOnReturn(Boolean.parseBoolean(settings.getTestOnReturn()));
		druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(settings.getPoolPreparedStatements()));
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(settings.getMaxPoolPreparedStatementPerConnectionSize()));
		druidDataSource.setProxyFilters(filteList);

		return druidDataSource;*/
	}


	@Bean
	public WallConfig getWallConfig(){
		WallConfig wallConfig = new WallConfig();
		wallConfig.setMultiStatementAllow(true);
		wallConfig.setCommentAllow(true);
		wallConfig.setMultiStatementAllow(true);
		return wallConfig;
	}

	@Bean
	public WallFilter getWallFilter(){
		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(getWallConfig());
		return wallFilter;
	}


	@Bean public PlatformTransactionManager transactionManager(DataSource druidDataSource) throws SQLException {
		return new DataSourceTransactionManager(druidDataSource);
	}
}

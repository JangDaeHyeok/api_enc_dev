package jdh.test.api;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/*
 * @ServletComponentScan
 * filter: @WebFilter
 * servlet: @WebServlet
 * listner: @WebListner
 * ë¥? ?Š¤ìº”í•´?„œ ë¹ˆìœ¼ë¡? ?“±ë¡?
 */
@ServletComponentScan
@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApiApplication.class);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		// MyBatis Mapper Source
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);
		
		String pkg = this.getClass().getPackage().getName();
		String[] pkgSplit = pkg.split("\\.");
		String pkgNm = pkgSplit[0] + "." + pkgSplit[1] + "." + pkgSplit[2];
		sessionFactory.setTypeAliasesPackage(pkgNm);
		// MyBatis Config setting
		Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
		sessionFactory.setConfigLocation(myBatisConfig);
		
		return sessionFactory.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}

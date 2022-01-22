package jdh.test.api;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiInitializer implements ApplicationRunner{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired SqlSessionFactory sf;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.error("[프로젝트 기동]");
	}
}

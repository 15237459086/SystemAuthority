package com.kurumi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置文件读取
 * @author lyh
 *
 */
@Configuration
@PropertySource(value="classpath:application.properties") 
public class MyConfig {

	
	@Value("${config.init.user.password}")
	private String initUserPassword;

	public String getInitUserPassword() {
		return initUserPassword;
	}

	public void setInitUserPassword(String initUserPassword) {
		this.initUserPassword = initUserPassword;
	}

	
	
	
	
	
}

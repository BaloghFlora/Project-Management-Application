package com.proj_mngmt.proj_mngmt;

import com.proj_mngmt.proj_mngmt.security.util.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class ProjMngmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjMngmtApplication.class, args);
	}

}

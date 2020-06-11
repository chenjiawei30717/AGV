package com.dear;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dear.mapper") 
public class AgvApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgvApplication.class, args);
	}

}

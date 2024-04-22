package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
		
		System.out.println(""
				+ "POST : localhost:8290/users/v1/authentication/register"
				+ ""
				+ ""
				+ ""
				+ "{\r\n"
				+ "    \"firstName\": \"John\",\r\n"
				+ "    \"lastName\": \"Doe\",\r\n"
				+ "    \"email\":\"john.doe@example.com\",\r\n"
				+ "    \"password\": \"secretpassword\",\r\n"
				+ "    \"role\": \"MEMBER\"\r\n"
				+ "}"
				+ ""
				+ ""
				+ ""
				+ ""
				+ "POST : localhost:8290/users/v1/authentication/authenticate"
				+ "{\r\n"
				+ "    \"email\":\"john.doe@example.com\",\r\n"
				+ "    \"password\": \"secretpassword\"\r\n"
				+ "}"
				+ ""
				+ ""
				+ "");
	}

}

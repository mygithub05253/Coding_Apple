package com.apple.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		// 스프링으로 만든 웹서버를 띄워달라는 의미
		SpringApplication.run(ShopApplication.class, args);
	}

}

package com.example.securityApp.securityApplication;

import com.example.securityApp.securityApplication.entity.User;
import com.example.securityApp.securityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest
class SecurityApplicationTests {
	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user = new User(4L, "jainath@gmail.com", "12355", "jainath");

		String token = jwtService.generateToken(user);

		System.out.println(token);

		Long userId = jwtService.getUserIdFromToken(token);

		System.out.println(userId);
	}

}

package com.abc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/v1/member")
@PreAuthorize("hasRole('ADMIN')")
public class MemberController {
	
	@GetMapping
	public String getMember() {
		return "Secured Endpoint :: GET-Member controller";
	}
	

}

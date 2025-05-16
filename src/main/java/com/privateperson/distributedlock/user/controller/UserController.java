package com.privateperson.distributedlock.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privateperson.distributedlock.user.dto.UserSaveRequest;
import com.privateperson.distributedlock.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	UserService userservice;

	@PostMapping
	public ResponseEntity<Void> createUser(UserSaveRequest request) {

		userservice.createUser(request);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}

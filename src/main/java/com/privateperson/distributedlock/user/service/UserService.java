package com.privateperson.distributedlock.user.service;

import org.springframework.stereotype.Service;

import com.privateperson.distributedlock.user.dto.UserSaveRequest;
import com.privateperson.distributedlock.user.model.Role;
import com.privateperson.distributedlock.user.model.User;
import com.privateperson.distributedlock.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void createUser(UserSaveRequest request) {

		if(userRepository.existsByEmail(request.email())) {
			throw new RuntimeException("유저 중복");
		}

		User user = User.builder()
			.name(request.name())
			.email(request.email())
			.password(request.password())
			.role(Role.valueOf(request.role().toUpperCase()))
			.build();

		userRepository.save(user);
	}

}

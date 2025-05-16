package com.privateperson.distributedlock.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.privateperson.distributedlock.user.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	boolean existsByEmail(String email);

}

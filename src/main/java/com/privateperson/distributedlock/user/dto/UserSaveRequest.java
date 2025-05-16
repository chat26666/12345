package com.privateperson.distributedlock.user.dto;
import jakarta.validation.constraints.Email;

public record UserSaveRequest (

	@Email
	String email,

	String name,

	String password,

	String role
){
}

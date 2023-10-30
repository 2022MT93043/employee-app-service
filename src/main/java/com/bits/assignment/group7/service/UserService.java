package com.bits.assignment.group7.service;

import com.bits.assignment.group7.dto.UserRegistrationDto;
import com.bits.assignment.group7.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}

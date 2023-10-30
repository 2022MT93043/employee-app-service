package com.bits.assignment.group7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}

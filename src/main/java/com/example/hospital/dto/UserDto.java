package com.example.hospital.dto;

import com.example.hospital.model.Role;
import com.example.hospital.model.User;

import lombok.*;


@Data
@NoArgsConstructor
public class UserDto {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;


	public UserDto(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.role = user.getRole();
	}
}

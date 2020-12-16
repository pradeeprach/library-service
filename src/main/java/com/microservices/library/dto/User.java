package com.microservices.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private Long id;
	private String firstname;
	private String lastname;
	private String emailID;
	private String phoneNumber;
	private String address;
}

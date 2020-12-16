package com.microservices.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ISSUED_BOOK")
@Getter
@Setter
public class IssuedBook {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "BOOK_ID")
	private Long bookID;
	
	@Column(name = "USER_ID")
	private Long userID;
}

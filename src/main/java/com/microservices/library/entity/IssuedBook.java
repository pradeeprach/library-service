package com.microservices.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ISSUED_BOOK")
@Getter
@Setter
public class IssuedBook {
	
	@Id
    @SequenceGenerator(name = "ISSUED_BOOK_GENERATOR_SEQ", sequenceName  = "ISSUED_BOOK_GENERATOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ISSUED_BOOK_GENERATOR_SEQ")
    @Column(name = "ISSUED_BOOK_ID", nullable = false)
	private Long id;
	
	@Column(name = "BOOK_ID")
	private Long bookID;
	
	@Column(name = "USER_ID")
	private Long userID;
}

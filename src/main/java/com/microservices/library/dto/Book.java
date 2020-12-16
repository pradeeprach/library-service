package com.microservices.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

	private Long id;
	private String title;
	private String author;
	private String year;
	private String publisher;
	private double price;
	private String isbn;
}

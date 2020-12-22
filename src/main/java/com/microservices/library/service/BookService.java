package com.microservices.library.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservices.library.dto.Book;

@FeignClient("book-service")
public interface BookService {

	@GetMapping("/books")
	public List<Book> getBooks();
	
	@GetMapping("/books/{book_id}")
	public Book getBookByID(@PathVariable(name = "book_id") Long bookID);
	
	@PostMapping("/books")
	public Book addBook(@RequestBody Book book);
	
	@DeleteMapping("/books/{book_id}")
	public String deleteBook(@PathVariable(name = "book_id") Long bookID);
	
	@PutMapping("books/{book_id}")
	public Book updateBook(@PathVariable(name = "book_id") Long bookID, @RequestBody Book book);
}

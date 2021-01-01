package com.microservices.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.library.dto.Book;
import com.microservices.library.dto.User;
import com.microservices.library.service.LibraryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	@GetMapping("/books")
	@HystrixCommand(fallbackMethod = "getFallbackResponseForAllBooks",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
		}
	)
	public List<Book> getAllBooks() {
		return libraryService.getAllBooks();
	}

	public List<Book> getFallbackResponseForAllBooks() {
		return new ArrayList<>();
	}
	
	@GetMapping("/books/{book_id}")
	public Book getBookById(@PathVariable(name = "book_id") Long bookID) {
		return libraryService.getBookByID(bookID);
	}
	
	@PostMapping("/books")
	public Book addBook(@RequestBody Book book) {
		return libraryService.addBook(book);
	}
	
	@DeleteMapping("/books/{book_id}")
	public boolean deleteBook(@PathVariable(name = "book_id") Long bookID) {
		return libraryService.deleteBook(bookID);
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return libraryService.getAllUsers();
	}
	
	@GetMapping("/users/{user_id}")
	public User getUserByID(@PathVariable(name = "user_id") Long userID) {
		return libraryService.getUserByID(userID);
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		return libraryService.addUser(user);
	}
	
	@DeleteMapping("/users/{user_id}")
	public boolean deleteUser(@PathVariable(name = "user_id") Long userID) {
		return libraryService.deleteUser(userID);
	}
	
	@PutMapping("users/{user_id}")
	public User updateUser(@PathVariable(name = "user_id") Long userID, @RequestBody User user) {
		return libraryService.updateUser(userID, user);
	}
	
	@PostMapping("users/{user_id}/books/{book_id}")
	public String issueBook(@PathVariable(name = "user_id") Long userID, 
			@PathVariable(name = "book_id") Long bookID) {
		String result = "Issue Unsuccessful";
		if (libraryService.issueBook(userID, bookID)) {
			result = "Issued Successfully";
		}
		return result;
	}
	
	@DeleteMapping("users/{user_id}/books/{book_id}")
	public String releaseBook(@PathVariable(name = "user_id") Long userID, 
			@PathVariable(name = "book_id") Long bookID) {
		String result = "Release Unsuccessful";
		if (libraryService.releaseBook(userID, bookID)) {
			result = "Released Successfully";
		}
		return result;
	}
}

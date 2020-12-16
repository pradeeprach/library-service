package com.microservices.library.service;

import java.util.List;

import com.microservices.library.dto.Book;
import com.microservices.library.dto.User;

public interface LibraryService {

	List<Book> getAllBooks();
	Book getBookByID(Long bookID);
	Book addBook(Book book);
	boolean deleteBook(Long bookID);
	List<User> getAllUsers();
	User getUserByID(Long userID);
	User addUser(User user);
	boolean deleteUser(Long userID);
	User updateUser(Long userID, User user);
	boolean issueBook(Long userID, Long bookID);
	boolean releaseBook(Long userID, Long bookID);
}

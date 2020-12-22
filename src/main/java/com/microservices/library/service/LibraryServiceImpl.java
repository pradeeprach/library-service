package com.microservices.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.library.dto.Book;
import com.microservices.library.dto.User;
import com.microservices.library.entity.IssuedBook;
import com.microservices.library.repository.IssuedBooksRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private IssuedBooksRepository issuedBooksRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;

	public List<Book> getAllBooks() {
		return bookService.getBooks();
	}

	public Book getBookByID(Long bookID) {
		return bookService.getBookByID(bookID);
	}

	public Book addBook(Book book) {
		return bookService.addBook(book);
	}

	public boolean deleteBook(Long bookID) {
		boolean isDeleted = true;
		bookService.deleteBook(bookID);
		Book book = getBookByID(bookID);
		if (book != null && book.getId() != null) {
			isDeleted = false;
		}
		return isDeleted;
	}

	public List<User> getAllUsers() {
		return userService.getUsers();
	}

	public User getUserByID(Long userID) {
		return userService.getUserByID(userID);
	}

	public User addUser(User user) {
		return userService.addUser(user);
	}

	public boolean deleteUser(Long userID) {
		boolean isDeleted = true;
		userService.deleteUser(userID);
		User user = getUserByID(userID);
		if (user != null && user.getId() != null) {
			isDeleted = false;
		}
		return isDeleted;
	}

	@Override
	public User updateUser(Long userID, User user) {
		userService.updateUser(userID, user);
		return getUserByID(userID);
	}

	public boolean issueBook(Long userID, Long bookID) {
		boolean isIssued = false;
		
		User user = getUserByID(userID);
		Book book = getBookByID(bookID);
		
		if (user != null && user.getId() != null
				&& book != null && book.getId() != null) {
			issuedBooksRepository.save(populateIssuedBook(bookID, userID));
			isIssued = true;
		}
		
		return isIssued;
	}

	@Transactional
	public boolean releaseBook(Long userID, Long bookID) {
		issuedBooksRepository.deleteByUserIDAndBookID(userID, bookID);
		return !issuedBooksRepository.existsByUserIDAndBookID(userID, bookID);
	}

	private IssuedBook populateIssuedBook(Long bookID, Long userID) {
		IssuedBook issuedBook = new IssuedBook();
		issuedBook.setBookID(bookID);
		issuedBook.setUserID(userID);
		return issuedBook;
	}
}

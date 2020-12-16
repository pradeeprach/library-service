package com.microservices.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.library.configuration.EndpointsConfiguration;
import com.microservices.library.dto.Book;
import com.microservices.library.dto.User;
import com.microservices.library.entity.IssuedBook;
import com.microservices.library.repository.IssuedBooksRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private IssuedBooksRepository issuedBooksRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired 
	private EndpointsConfiguration endpointsConfiguration;

	public List<Book> getAllBooks() {
		return restTemplate.exchange(endpointsConfiguration.getBookEndpoint(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Book>>() {}).getBody();
	}

	public Book getBookByID(Long bookID) {
		return restTemplate.getForObject(endpointsConfiguration.getBookEndpoint() + "/" + bookID, Book.class);
	}

	public Book addBook(Book book) {
		return restTemplate.postForObject(endpointsConfiguration.getBookEndpoint(), book, Book.class);
	}

	public boolean deleteBook(Long bookID) {
		boolean isDeleted = true;
		restTemplate.delete(endpointsConfiguration.getBookEndpoint() + "/" + bookID);
		Book book = getBookByID(bookID);
		if (book != null && book.getId() != null) {
			isDeleted = false;
		}
		return isDeleted;
	}

	public List<User> getAllUsers() {
		return restTemplate.exchange(endpointsConfiguration.getUserEndpoint(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {}).getBody();
	}

	public User getUserByID(Long userID) {
		return restTemplate.getForObject(endpointsConfiguration.getUserEndpoint() + "/" + userID, User.class);
	}

	public User addUser(User user) {
		return restTemplate.postForObject(endpointsConfiguration.getUserEndpoint(), user, User.class);
	}

	public boolean deleteUser(Long userID) {
		boolean isDeleted = true;
		restTemplate.delete(endpointsConfiguration.getUserEndpoint() + "/" + userID);
		User user = getUserByID(userID);
		if (user != null && user.getId() != null) {
			isDeleted = false;
		}
		return isDeleted;
	}

	@Override
	public User updateUser(Long userID, User user) {
		restTemplate.put(endpointsConfiguration.getUserEndpoint() + "/" + userID, user);
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

package com.microservices.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.library.entity.IssuedBook;

@Repository
public interface IssuedBooksRepository extends JpaRepository<IssuedBook, Long>{
	
	void deleteByUserIDAndBookID(Long userID, Long bookID);
	
	boolean existsByUserIDAndBookID(Long userID, Long bookID);
}

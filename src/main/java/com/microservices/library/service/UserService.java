package com.microservices.library.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservices.library.dto.User;

@FeignClient("user-service")
public interface UserService {

	@GetMapping("/users")
	public List<User> getUsers();
	
	@GetMapping("/users/{user_id}")
	public User getUserByID(@PathVariable(name = "user_id") Long userID);
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user);
	
	@DeleteMapping("/users/{user_id}")
	public String deleteUser(@PathVariable(name = "user_id") Long userID);
	
	@PutMapping("users/{user_id}")
	public User updateUser(@PathVariable(name = "user_id") Long userID, @RequestBody User user);
}

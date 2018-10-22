package com.springcloud.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class ApplicationService {
	@GetMapping("{id}")
	public void get(@PathVariable("id")String id) {
		
	}
	@PutMapping("/save")
	public void update(String entity) {
		
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")String id) {
		
	}
	@PostMapping("/save")
	public void save(String entity) {
		
	}
}

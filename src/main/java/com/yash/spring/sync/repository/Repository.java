package com.yash.spring.sync.repository;

import java.util.Optional;   

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.spring.sync.entity.Employee;


public interface Repository extends JpaRepository<Employee,Integer> {

	

}

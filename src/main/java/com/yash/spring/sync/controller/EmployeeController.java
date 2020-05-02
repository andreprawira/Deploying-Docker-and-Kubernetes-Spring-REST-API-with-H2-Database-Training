package com.yash.spring.sync.controller;

import java.util.List;   
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.yash.spring.sync.entity.Employee;
import com.yash.spring.sync.service.EmployeeServiceImpl;

import lombok.Synchronized;

import com.yash.spring.sync.service.EmployeeService;

@RestController
@RequestMapping("/api")

public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl service;
    
    @Synchronized
    @PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		return ResponseEntity.ok().body(this.service.saveEmployee(employee));
	}     
    
    @Synchronized
    @GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.getEmployeeById(id));
	}         
    
    @Synchronized
    @PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee){
		employee.setId(id);
		return ResponseEntity.ok().body(this.service.updateEmployee(employee));
	}   
    
    @Synchronized
    @DeleteMapping("/employees/{id}")
	public HttpStatus deleteEmployee(@PathVariable Integer id){
		this.service.deleteEmployee(id);
		return HttpStatus.OK;
	}
    
    @Synchronized
    @GetMapping(value = "/employees")
    public CompletableFuture<ResponseEntity> findAllEmployee() {
       return  service.findAllEmployee().thenApply(ResponseEntity::ok);
    }
    
//    @GetMapping(value = "/getEmployeeByThread", produces = "application/json")
//    public  ResponseEntity getEmployee(){
//        CompletableFuture<List<Employee>> employee1=service.findAllEmployee();
//        CompletableFuture<List<Employee>> employee2=service.findAllEmployee();
//        CompletableFuture<List<Employee>> employee3=service.findAllEmployee();
//        CompletableFuture.allOf(employee1,employee2,employee3).join();
//        return  ResponseEntity.status(HttpStatus.OK).build();
//    }
    
    
//  @PostMapping(value = "/employees", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
//  public ResponseEntity saveEmployee(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
//      for (MultipartFile file : files) {
//          service.saveEmployee(file);
//      }
//      return ResponseEntity.status(HttpStatus.CREATED).build();
//  }
}

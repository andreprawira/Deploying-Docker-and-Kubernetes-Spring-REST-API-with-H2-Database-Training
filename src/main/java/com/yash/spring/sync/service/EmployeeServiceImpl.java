package com.yash.spring.sync.service;

import java.io.BufferedReader;     
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.yash.spring.sync.entity.Employee;
import com.yash.spring.sync.exception.ResourceNotFoundException;
import com.yash.spring.sync.repository.Repository;
import lombok.Synchronized;


@Service
@Transactional
public class EmployeeServiceImpl {

    @Autowired
    private Repository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    
    @Synchronized
    public CompletableFuture<List<Employee>> findAllEmployee(){
    	logger.info("Thread "+Thread.currentThread().getName()+" is retrieving list of employee");
    	List<Employee> employees=repository.findAll();
    	return CompletableFuture.completedFuture(employees);
    }

    @Synchronized
    public Employee saveEmployee(Employee employee) {
    	return repository.save(employee);
    }

    @Synchronized
    private List<Employee> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Employee> employees = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Employee employee = new Employee();
                    employee.setName(data[0]);
                    employee.setSalary(data[1]);                 
                    employees.add(employee);
                }
                return employees;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
    @Synchronized
    public void deleteEmployee(Integer employeeId) {
		Optional<Employee> employeeDb = this.repository.findById(employeeId);
		
		if(employeeDb.isPresent()) {
			this.repository.delete(employeeDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + employeeId);
		}
		
	}
    @Synchronized   
	public Employee getEmployeeById(Integer employeeId) {
		
		Optional<Employee> employeeDb = this.repository.findById(employeeId);
		
		if(employeeDb.isPresent()) {
			return employeeDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + employeeId);
		}
	}
    @Synchronized
	public Employee updateEmployee(Employee employee) {
		Optional<Employee> employeeDb = this.repository.findById(employee.getId());
		
		if(employeeDb.isPresent()) {
			Employee employeeUpdate = employeeDb.get();
			employeeUpdate.setId(employee.getId());
			employeeUpdate.setName(employee.getName());
			employeeUpdate.setSalary(employee.getSalary());
			repository.save(employeeUpdate);
			return employeeUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + employee.getId());
		}		
	}
//  @Synchronized
//  public CompletableFuture<List<Employee>> saveEmployee(MultipartFile file) throws Exception {
//      long start = System.currentTimeMillis();
//      List<Employee> employees = parseCSVFile(file);
//      logger.info("Saving list of employee of size {} records", employees.size(), "" + Thread.currentThread().getName());
//      employees = repository.saveAll(employees);
//      long end = System.currentTimeMillis();
//      logger.info("Total time {} ms", (end - start));
//      return CompletableFuture.completedFuture(employees);
//  }
}

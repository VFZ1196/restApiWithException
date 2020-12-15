package com.springboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	
	//Injecting bean into another bean
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Getting all employee
	@GetMapping("/employee")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	//Getting employee by id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)throws ResourceNotFoundException{

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for id :"+ id));
		return ResponseEntity.ok().body(employee);
	}
	
	//Getting employee  by multiple Parameter
	@GetMapping("/employee/{id}/{firstname}")
	public ResponseEntity<Employee> getEmployeeByMultipleParam(@PathVariable Long id, @PathVariable String firstname)
			throws ResourceNotFoundException{

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for id :"+ id));
		return ResponseEntity.ok().body(employee);
	}
		
	//Adding new employee
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//Deleting employee by id
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Void> deleteEmpById(@PathVariable Long id) throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: "+id));
		employeeRepository.delete(employee);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//Updating employee by id
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails)
	throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for id :"+ id));
		employee.setEmail(employeeDetails.getEmail());
		employee.setFirstname(employeeDetails.getFirstname());
		employee.setLastname(employeeDetails.getLastname());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

}

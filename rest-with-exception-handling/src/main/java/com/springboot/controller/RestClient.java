package com.springboot.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springboot.model.Employee;

public class RestClient {
	
	private static final String get_All_Users = "http://localhost:8083/employee";
	
	private static final String get_User_By_Id = "http://localhost:8083/employee/{id}";

	private static final String create_User = "http://localhost:8083/employee";

	private static final String delete_User_By_Id = "http://localhost:8083/employee/{id}";
	
	static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		callGetAllUsers();
		callGetUserById();
		callCreateUser();
		callDeleteUser();
	}
	
	private static void callGetAllUsers() {
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<>("Parameter",header);
		
		ResponseEntity<String> result = 
				restTemplate.exchange(get_All_Users, HttpMethod.GET, entity, String.class);
		System.out.println(result);
	}
	
	private static void callGetUserById() {
		Map<String,Integer> param = new HashMap<>();
		param.put("id",1);
		Employee emp = restTemplate.getForObject(get_User_By_Id, Employee.class,param);
		System.out.println(emp.getFirstname());
		System.out.println(emp.getLastname());
		System.out.println(emp.getEmail());

	}
	
	private static void callCreateUser() {
		Employee employee = new Employee("Ranga","Karnam","rng@gmail.com");
		ResponseEntity<Employee> emp = 
				restTemplate.postForEntity(create_User, employee, Employee.class);
		System.out.println(emp.getBody());
	}
	
	private static void callDeleteUser() {
		Map<String,Integer> param = new HashMap<>();
		param.put("id",3);
		restTemplate.delete(delete_User_By_Id,param);
	}

}

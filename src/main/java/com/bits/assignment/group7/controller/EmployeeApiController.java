package com.bits.assignment.group7.controller;

import java.util.List;

import com.bits.assignment.group7.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bits.assignment.group7.model.Employee;
import com.bits.assignment.group7.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeApiController {

	@Autowired
	private EmployeeService employeeService;
	
	// For rest api calls
	@GetMapping("/{id}")
	public EmployeeDTO get(@PathVariable long id) {
		return employeeService.getEmployeeById(id);
	}
	@GetMapping("/")
	public List<EmployeeDTO> getAll() {
		return employeeService.getAllEmployees();
	}

	@PostMapping("/saveEmployee")
	public EmployeeDTO saveEmp(@ModelAttribute("employee") EmployeeDTO dto) {
		// save employee to database
		EmployeeDTO savedDto = employeeService.saveEmployee(dto);
		return savedDto;
	}

	@GetMapping("/updateEmployee/{id}")
	public EmployeeDTO showFormForUpdate(@PathVariable ( value = "id") long id, EmployeeDTO employee) {
		// get employee from the service
		EmployeeDTO emp = employeeService.getEmployeeById(id);
		BeanUtils.copyProperties(employee, emp);
		// set employee as a model attribute to pre-populate the form
		EmployeeDTO savedEmployee = employeeService.saveEmployee(emp);
		return savedEmployee;
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmp(@PathVariable (value = "id") long id) {
		// call delete employee method
		this.employeeService.deleteEmployeeById(id);
		return "Successfully deleted employee ID:" + id;
	}

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("Microservice 1: Employee Service is up and running....!");
	}
}
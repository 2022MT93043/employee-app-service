package com.bits.assignment.group7.service;

import java.util.List;

import com.bits.assignment.group7.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

public interface EmployeeService {
	List<EmployeeDTO> getAllEmployees();
	EmployeeDTO saveEmployee(EmployeeDTO employee);
	EmployeeDTO getEmployeeById(long id);
	void deleteEmployeeById(long id);
	Page<EmployeeDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

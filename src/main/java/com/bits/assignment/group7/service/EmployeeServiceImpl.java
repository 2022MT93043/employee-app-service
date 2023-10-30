package com.bits.assignment.group7.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bits.assignment.group7.dto.DepartmentDTO;
import com.bits.assignment.group7.dto.EmployeeDTO;
import com.bits.assignment.group7.dto.OrganizationDTO;
import com.bits.assignment.group7.model.Employee;
import com.bits.assignment.group7.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	static String ORGANIZATION_URL = "http://localhost:8090/api/organizations";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream()
				.map(e -> new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(), e.getOrganizationId(), getOrganizationById(e.getOrganizationId())))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO dto) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(dto, employee);
		employee = this.employeeRepository.save(employee);
		BeanUtils.copyProperties(employee, dto);
		return dto;
	}

	@Override
	public EmployeeDTO getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		EmployeeDTO dto = new EmployeeDTO();
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
			BeanUtils.copyProperties(employee, dto);
			dto.setOrganization(getOrganizationById(employee.getOrganizationId()));
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return dto;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<EmployeeDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		Page<Employee> entityPage = this.employeeRepository.findAll(pageable);
		Page<EmployeeDTO> dtoPage = entityPage.map(entity -> {
			EmployeeDTO dto = entityToDto(entity);
			return dto;
		});
		return dtoPage;
	}

	private OrganizationDTO getOrganizationById(long id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrganizationDTO> response = restTemplate.getForEntity(ORGANIZATION_URL + "/" + id, OrganizationDTO.class);
		return response.getBody();
	}

	public EmployeeDTO entityToDto(Employee entity){
		EmployeeDTO dto =  new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setOrganizationId(entity.getOrganizationId());
		dto.setOrganization(getOrganizationById(entity.getId()));
		return dto;
	}
}
package com.bits.assignment.group7.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrganizationDTO {
	private Long id;
	private String name;
	private String code;
	private String address;
	private Long departmentId;
	private List<DepartmentDTO> departments = new ArrayList<>();
}

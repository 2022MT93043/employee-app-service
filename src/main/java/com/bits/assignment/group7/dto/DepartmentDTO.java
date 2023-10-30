package com.bits.assignment.group7.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DepartmentDTO {
	private Long id;
	private Long organizationId;
	private String name;
	private String code;
	private String address;
}

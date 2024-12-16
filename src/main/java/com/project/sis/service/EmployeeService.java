package com.project.sis.service;

import com.project.sis.dto.EmployeeDTO;
import com.project.sis.dto.EmployeeUpdateDTO;
import com.project.sis.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {


    Employee createEmployee(EmployeeDTO dto);

    Employee getEmployeeById(Long id);

    Employee updateEmployee(EmployeeUpdateDTO updateDTO, Long id);

    void deleteEmployee(Long id);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable);
}

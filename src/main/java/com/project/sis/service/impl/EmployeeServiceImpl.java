package com.project.sis.service.impl;

import com.project.sis.common.Check;
import com.project.sis.dto.EmployeeDTO;
import com.project.sis.dto.EmployeeUpdateDTO;
import com.project.sis.entity.Employee;
import com.project.sis.mapper.EmployeeMapper;
import com.project.sis.repository.EmployeeRepository;
import com.project.sis.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee createEmployee(EmployeeDTO dto) {
        log.info("Registering employee with email: {}", dto.getEmail());

        if (employeeRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = employeeMapper.toEntity(dto);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);
        employee = employeeRepository.save(employee);

        log.info("Employee registered successfully with ID: {}", employee.getId());
        return employee;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee by ID: {}", id);

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Check.throwIfEmpty(employeeOptional, new NoSuchElementException("Employee not found with ID: " + id));

        return employeeOptional.get();
    }

    @Override
    public Employee updateEmployee(EmployeeUpdateDTO updateDTO, Long id) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = this.getEmployeeById(id);

        if (employeeRepository.findByEmail(updateDTO.getEmail()).isPresent() &&
                !employee.getEmail().equals(updateDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        employee.setFirstName(updateDTO.getFirstName());
        employee.setLastName(updateDTO.getLastName());
        employee.setEmail(updateDTO.getEmail());
        employee.setSalary(updateDTO.getSalary());

        employee = employeeRepository.save(employee);

        log.info("Employee updated successfully with ID: {}", id);
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);

        Employee employee = this.getEmployeeById(id);
        employee.setStatus(Employee.EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(employeeMapper::toDTO);
    }
}
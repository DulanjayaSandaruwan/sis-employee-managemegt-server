package com.project.sis.controller;

import com.project.sis.dto.EmployeeDTO;
import com.project.sis.dto.EmployeeUpdateDTO;
import com.project.sis.entity.Employee;
import com.project.sis.mapper.EmployeeMapper;
import com.project.sis.service.EmployeeService;
import com.project.sis.util.ApiResponse;
import com.project.sis.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.createEmployee(employeeDTO);
        EmployeeDTO dto = employeeMapper.toDTO(employee);
        return ResponseUtil.created(dto, "Employee created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeDTO dto = employeeMapper.toDTO(employee);
        return ResponseUtil.ok(dto, "Employee retrieved successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO updateDTO) {
        Employee employee = employeeService.updateEmployee(updateDTO, id);
        EmployeeDTO dto = employeeMapper.toDTO(employee);
        return ResponseUtil.ok(dto, "Employee updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        String successMessage = "Employee deleted successfully with ID: " + id;
        return ResponseUtil.deleted(successMessage);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeDTO>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<EmployeeDTO> employeeDTOPage = employeeService.getAllEmployees(pageable);
        return ResponseUtil.ok(employeeDTOPage, "Employees retrieved successfully.");
    }
}
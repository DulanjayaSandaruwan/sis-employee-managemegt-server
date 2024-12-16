package com.project.sis.service.impl;

import com.project.sis.dto.EmployeeDTO;
import com.project.sis.dto.EmployeeUpdateDTO;
import com.project.sis.entity.Employee;
import com.project.sis.mapper.EmployeeMapper;
import com.project.sis.repository.EmployeeRepository;
import com.project.sis.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeMapper);
    }

    @Test
    void testCreateEmployee_Success() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("Kumar");
        employeeDTO.setLastName("Sangakkara");
        employeeDTO.setEmail("kumar.sangakkara@example.com");
        employeeDTO.setSalary(50000.0);
        employeeDTO.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Kumar");
        employee.setLastName("Sangakkara");
        employee.setEmail("kumar.sangakkara@example.com");
        employee.setSalary(50000.0);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);

        when(employeeRepository.findByEmail(employeeDTO.getEmail())).thenReturn(Optional.empty());
        when(employeeMapper.toEntity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        assertEquals("Kumar", createdEmployee.getFirstName());
        assertEquals("Sangakkara", createdEmployee.getLastName());
        assertEquals("kumar.sangakkara@example.com", createdEmployee.getEmail());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testCreateEmployee_EmailAlreadyExists() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Mahela");
        employeeDTO.setLastName("Jayawardene");
        employeeDTO.setEmail("mahela.jayawardene@example.com");
        employeeDTO.setSalary(50000.0);

        when(employeeRepository.findByEmail(employeeDTO.getEmail())).thenReturn(Optional.of(new Employee()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });

        assertEquals("Email already exists", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }

    @Test
    void testGetEmployeeById_Success() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Lasith");
        employee.setLastName("Malinga");
        employee.setEmail("lasith.malinga@example.com");
        employee.setSalary(50000.0);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        assertEquals("Employee not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateEmployee_Success() {
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        updateDTO.setFirstName("Angelo");
        updateDTO.setLastName("Mathews");
        updateDTO.setEmail("angelo.mathews@example.com");
        updateDTO.setSalary(60000.0);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setFirstName("Angelo");
        existingEmployee.setLastName("Mathews");
        existingEmployee.setEmail("angelo.mathews@example.com");
        existingEmployee.setSalary(50000.0);
        existingEmployee.setStatus(Employee.EmployeeStatus.ACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee updatedEmployee = employeeService.updateEmployee(updateDTO, 1L);

        assertNotNull(updatedEmployee);
        assertEquals("Angelo", updatedEmployee.getFirstName());
        assertEquals("Mathews", updatedEmployee.getLastName());
        assertEquals("angelo.mathews@example.com", updatedEmployee.getEmail());
        assertEquals(60000.0, updatedEmployee.getSalary());
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void testUpdateEmployee_EmailAlreadyExists() {
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        updateDTO.setFirstName("Dinesh");
        updateDTO.setLastName("Chandimal");
        updateDTO.setEmail("dinesh.chandimal@example.com");
        updateDTO.setSalary(60000.0);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setFirstName("Dinesh");
        existingEmployee.setLastName("Chandimal");
        existingEmployee.setEmail("dinesh.chandimal@example.com");
        existingEmployee.setSalary(50000.0);
        existingEmployee.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee otherEmployee = new Employee();
        otherEmployee.setId(2L);
        otherEmployee.setFirstName("Dimuth");
        otherEmployee.setLastName("Karunaratne");
        otherEmployee.setEmail("dinesh.chandimal@example.com");
        otherEmployee.setSalary(60000.0);
        otherEmployee.setStatus(Employee.EmployeeStatus.ACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.of(otherEmployee));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(updateDTO, 1L);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void testDeleteEmployee_Success() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Suranga");
        employee.setLastName("Lakmal");
        employee.setEmail("suranga.lakmal@example.com");
        employee.setSalary(50000.0);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        assertEquals(Employee.EmployeeStatus.INACTIVE, employee.getStatus());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployees() {
        Pageable pageable = Pageable.unpaged();
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Chaminda");
        employee1.setLastName("Vaas");
        employee1.setEmail("chaminda.vaas@example.com");
        employee1.setSalary(50000.0);
        employee1.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Tillekaratne");
        employee2.setLastName("Dilshan");
        employee2.setEmail("tillekaratne.dilshan@example.com");
        employee2.setSalary(60000.0);
        employee2.setStatus(Employee.EmployeeStatus.ACTIVE);

        List<Employee> employees = List.of(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        when(employeeMapper.toDTO(employee1)).thenReturn(new EmployeeDTO(1L, "Chaminda", "Vaas", "chaminda.vaas@example.com", 50000.0, Employee.EmployeeStatus.ACTIVE));
        when(employeeMapper.toDTO(employee2)).thenReturn(new EmployeeDTO(2L, "Tillekaratne", "Dilshan", "tillekaratne.dilshan@example.com", 60000.0, Employee.EmployeeStatus.ACTIVE));

        Page<EmployeeDTO> employeeDTOPage = employeeService.getAllEmployees(pageable);

        assertNotNull(employeeDTOPage);
        assertEquals(2, employeeDTOPage.getTotalElements());
        verify(employeeRepository, times(1)).findAll(pageable);
    }
}
package com.example.employee_payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeePayrollDTO> getAllEmployees() {
        log.debug("Fetching all employees from repository");
        List<EmployeePayrollDTO> employees = employeeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        log.info("Retrieved {} employees", employees.size());
        return employees;
    }

    public EmployeePayrollDTO getEmployeeById(Long id) {
        log.debug("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee with ID {} not found", id);
                    return new EmployeeValidationException("Employee with ID " + id + " not found");
                });
        log.info("Found employee with ID: {}", id);
        return toDTO(employee);
    }

    public EmployeePayrollDTO createEmployee(EmployeePayrollDTO employeeDTO) {
        log.debug("Attempting to create employee: {}", employeeDTO);
        Employee employee = toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully created employee with ID: {}", savedEmployee.getId());
        return toDTO(savedEmployee);
    }

    public EmployeePayrollDTO updateEmployee(Long id, EmployeePayrollDTO employeeDTO) {
        log.debug("Attempting to update employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee with ID {} not found", id);
                    return new EmployeeValidationException("Employee with ID " + id + " not found");
                });
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with ID: {}", id);
        return toDTO(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        log.debug("Attempting to delete employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            log.info("Successfully deleted employee with ID: {}", id);
        } else {
            log.error("Employee with ID {} not found", id);
            throw new EmployeeValidationException("Employee with ID " + id + " not found");
        }
    }

    public List<EmployeePayrollDTO> getEmployeesByDepartment(String department) {
        log.debug("Fetching employees in department: {}", department);
        List<EmployeePayrollDTO> employees = employeeRepository.findByDepartment(department).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        log.info("Retrieved {} employees in department: {}", employees.size(), department);
        return employees;
    }

    public List<EmployeePayrollDTO> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        log.debug("Fetching employees with salary between {} and {}", minSalary, maxSalary);
        if (minSalary > maxSalary) {
            log.error("Invalid salary range: minSalary {} is greater than maxSalary {}", minSalary, maxSalary);
            throw new EmployeeValidationException("Invalid salary range: minSalary cannot be greater than maxSalary");
        }
        List<EmployeePayrollDTO> employees = employeeRepository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        log.info("Retrieved {} employees with salary between {} and {}", employees.size(), minSalary, maxSalary);
        return employees;
    }

    private EmployeePayrollDTO toDTO(Employee employee) {
        return new EmployeePayrollDTO(
                employee.getName(),
                employee.getSalary(),
                employee.getDepartment()
        );
    }

    private Employee toEntity(EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());
        return employee;
    }
}
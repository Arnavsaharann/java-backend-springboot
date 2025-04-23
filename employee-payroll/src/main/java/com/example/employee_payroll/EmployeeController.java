package com.example.employee_payroll;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeePayrollDTO>> getAllEmployees() {
        log.debug("Received GET request to fetch all employees");
        List<EmployeePayrollDTO> employees = employeeService.getAllEmployees();
        log.info("Returning {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeePayrollDTO> getEmployeeById(@PathVariable Long id) {
        log.debug("Received GET request to fetch employee with ID: {}", id);
        EmployeePayrollDTO employee = employeeService.getEmployeeById(id);
        log.info("Returning employee with ID: {}", id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeePayrollDTO> createEmployee(@Valid @RequestBody EmployeePayrollDTO employeeDTO) {
        log.debug("Received POST request to create employee: {}", employeeDTO);
        EmployeePayrollDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        log.info("Created employee with ID: {}", createdEmployee.getSalary());
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeePayrollDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeePayrollDTO employeeDTO) {
        log.debug("Received PUT request to update employee with ID: {}", id);
        EmployeePayrollDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        log.info("Updated employee with ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("Received DELETE request for employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        log.info("Deleted employee with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department")
    public ResponseEntity<List<EmployeePayrollDTO>> getEmployeesByDepartment(@RequestParam String department) {
        log.debug("Received GET request to fetch employees in department: {}", department);
        List<EmployeePayrollDTO> employees = employeeService.getEmployeesByDepartment(department);
        log.info("Returning {} employees in department: {}", employees.size(), department);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary")
    public ResponseEntity<List<EmployeePayrollDTO>> getEmployeesBySalaryRange(
            @RequestParam double minSalary, @RequestParam double maxSalary) {
        log.debug("Received GET request to fetch employees with salary between {} and {}", minSalary, maxSalary);
        List<EmployeePayrollDTO> employees = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
        log.info("Returning {} employees with salary between {} and {}", employees.size(), minSalary, maxSalary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/form")
    public String showForm() {
        log.debug("Received GET request to show employee form");
        return "index";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            log.error("Validation error - Field: {}, Message: {}", error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(errors);
    }
}
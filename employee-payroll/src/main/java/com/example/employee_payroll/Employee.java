package com.example.employee_payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    private static final Logger log = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @Min(value = 0, message = "Salary cannot be negative")
    private double salary;

    private String department;

    public Employee(Long id, String name, double salary, String department) {
        log.debug("Creating Employee with id: {}, name: {}, salary: {}, department: {}", id, name, salary, department);

        if (name != null && name.trim().isEmpty()) {
            log.error("Invalid employee name: name cannot be empty or whitespace");
            throw new EmployeeValidationException("Name cannot be empty or whitespace");
        }
        if (salary < 0) {
            log.error("Invalid employee salary: salary cannot be negative (provided: {})", salary);
            throw new EmployeeValidationException("Salary cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        log.info("Successfully created Employee with id: {}", id);
    }
}
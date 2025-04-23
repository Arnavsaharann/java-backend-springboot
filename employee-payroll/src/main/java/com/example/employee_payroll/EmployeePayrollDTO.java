package com.example.employee_payroll;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeePayrollDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Double salary;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    public EmployeePayrollDTO(String name, Double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
}
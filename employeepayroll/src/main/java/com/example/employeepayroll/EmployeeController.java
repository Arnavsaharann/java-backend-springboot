package com.example.employeepayroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // UC1 & UC2: GET all employees (Read operation)
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    // Test with curl: curl "http://localhost:8080/employee" -w "\n"
    // Expected output: [] (or list of employees if added)

    // UC1 & UC2: GET employee by ID (Read operation)
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    // Test with curl: curl "http://localhost:8080/employee/1" -w "\n"
    // Expected output: {"id":1,"name":"Kanika Agarwal","salary":50000.0,"department":"HR"}
    // Precondition: Must have an employee with id=1 (created via POST)

    // UC1 & UC2: POST to create a new employee (Create operation)
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }
    // Test with curl: curl -X POST -H "Content-Type: application/json" -d '{"name":"Kanika Agarwal","salary":50000.0,"department":"HR"}' "http://localhost:8080/employee" -w "\n"
    // Expected output: {"id":1,"name":"Kanika Agarwal","salary":50000.0,"department":"HR"}

    // UC1 & UC2: PUT to update an existing employee (Update operation)
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
    // Test with curl: curl -X PUT -H "Content-Type: application/json" -d '{"name":"Kanika Agarwal","salary":55000.0,"department":"HR"}' "http://localhost:8080/employee/1" -w "\n"
    // Expected output: {"id":1,"name":"Kanika Agarwal","salary":55000.0,"department":"HR"}
    // Precondition: Must have an employee with id=1 (created via POST)

    // UC1 & UC2: DELETE an employee (Delete operation)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
         employeeService.deleteEmployee(id);
    }
    // Test with curl: curl -X DELETE "http://localhost:8080/employee/1" -w "\n"
    // Expected output: (No content, HTTP 204 status)
    // Verify deletion: curl "http://localhost:8080/employee" -w "\n"
    // Expected output: [] (or other remaining employees)
    // Precondition: Must have an employee with id=1 to delete
}
package com.example.SpringApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // UC1: Basic GET
    @GetMapping("/basic")
    public Greeting helloWorld() {
        return new Greeting("Hello World - GET");
    }
    // Test with curl: curl "http://localhost:8080/greeting/basic" -w "\n"
    // Expected output: {"message":"Hello World - GET"}

    // UC1: POST with Body
    @PostMapping("/basic")
    public Greeting helloWithBody(@RequestBody NameRequest nameRequest) {
        return new Greeting("Hello " + nameRequest.getFirstName() + " " + nameRequest.getLastName() + " - POST");
    }
    // Test with curl: curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Kanika","lastName":"Agarwal"}' "http://localhost:8080/greeting/basic" -w "\n"
    // Expected output: {"message":"Hello Kanika Agarwal - POST"}

    // UC1: PUT with Path Variable and Query Parameter
    @PutMapping("/basic/{firstName}")
    public Greeting helloWithPut(@PathVariable String firstName, @RequestParam String lastName) {
        return new Greeting("Hello " + firstName + " " + lastName + " - PUT");
    }
    // Test with curl: curl -X PUT "http://localhost:8080/greeting/basic/Arnav?lastName=Saharan" -w "\n"
    // Expected output: {"message":"Hello Arnav Saharan - PUT"}

    // UC2: Get a simple greeting using service layer
    @GetMapping
    public Greeting getSimpleGreeting() {
        return new Greeting(greetingService.getGreeting());
    }
    // Test with curl: curl "http://localhost:8080/greeting" -w "\n"
    // Expected output: {"message":"Hello World"}

    // UC3: Get a custom greeting based on first and last name
    @PostMapping("/custom")
    public Greeting getCustomGreeting(@RequestBody NameRequest user) {
        return new Greeting(greetingService.getCustomGreeting(user));
    }
    // Test with curl (both names): curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Kanika","lastName":"Agarwal"}' "http://localhost:8080/greeting/custom" -w "\n"
    // Expected output: {"message":"Hello Kanika Agarwal"}
    // Test with curl (first name only): curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Kanika"}' "http://localhost:8080/greeting/custom" -w "\n"
    // Expected output: {"message":"Hello Kanika"}
    // Test with curl (last name only): curl -X POST -H "Content-Type: application/json" -d '{"lastName":"Agarwal"}' "http://localhost:8080/greeting/custom" -w "\n"
    // Expected output: {"message":"Hello Agarwal"}
    // Test with curl (no names): curl -X POST -H "Content-Type: application/json" -d '{}' "http://localhost:8080/greeting/custom" -w "\n"
    // Expected output: {"message":"Hello World"}

    // UC4: Save a greeting message
    @PostMapping
    public GreetingDetails saveGreeting(@RequestBody Greeting greeting) {
        return greetingService.saveGreeting(greeting.getMessage());
    }
    // Test with curl: curl -X POST -H "Content-Type: application/json" -d '{"message":"Hello Arnav"}' "http://localhost:8080/greeting" -w "\n"
    // Expected output: {"id":1,"message":"Hello Arnav"}

    // UC5: Find a greeting by ID
    @GetMapping("/{id}")
    public GreetingDetails findGreetingById(@PathVariable Long id) {
        return greetingService.findGreetingById(id)
                .orElseThrow(() -> new RuntimeException("Greeting with ID " + id + " not found"));
    }
    // Test with curl: curl "http://localhost:8080/greeting/1" -w "\n"
    // Expected output: {"id":1,"message":"Hello Arnav"}
    // Precondition: Must have saved a greeting with id=1 using UC4

    // UC6: List all greetings
    @GetMapping("/all")
    public List<GreetingDetails> findAllGreetings() {
        return greetingService.findAllGreetings();
    }
    // Test with curl (after saving multiple greetings):
    // First, save another greeting: curl -X POST -H "Content-Type: application/json" -d '{"message":"Hello Kanika"}' "http://localhost:8080/greeting" -w "\n"
    // Expected output: {"id":2,"message":"Hello Kanika"}
    // Then list all: curl "http://localhost:8080/greeting/all" -w "\n"
    // Expected output: [{"id":1,"message":"Hello Arnav"},{"id":2,"message":"Hello Kanika"}]

    // UC7: Edit a greeting
    @PutMapping("/{id}")
    public GreetingDetails updateGreeting(@PathVariable Long id, @RequestBody Greeting greeting) {
        return greetingService.updateGreeting(id, greeting.getMessage());
    }
    // Test with curl: curl -X PUT -H "Content-Type: application/json" -d '{"message":"Hello Arnav Saharan"}' "http://localhost:8080/greeting/1" -w "\n"
    // Expected output: {"id":1,"message":"Hello Arnav Saharan"}
    // Precondition: Must have a greeting with id=1 saved using UC4

    // UC8: Delete a greeting
    @DeleteMapping("/{id}")
    public void deleteGreeting(@PathVariable Long id) {
        greetingService.deleteGreeting(id);
    }
    // Test with curl: curl -X DELETE "http://localhost:8080/greeting/1" -w "\n"
    // Expected output: (No content, HTTP 204 status)
    // Verify deletion: curl "http://localhost:8080/greeting/all" -w "\n"
    // Expected output: [{"id":2,"message":"Hello Kanika"}]
    // Precondition: Must have a greeting with id=1 to delete
}
package com.example.SpringApp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    // UC1: Basic GET
    @GetMapping
    public String helloWorld() {
        return "Hello from BridgeLabz";
    }

    // UC2: GET with Query Parameter
    @GetMapping("/query")
    public String helloWithQuery(@RequestParam String name) {
        return "Hello " + name + " from BridgeLabz";
    }

    // UC3: GET with Path Variable
    @GetMapping("/param/{name}")
    public String helloWithPath(@PathVariable String name) {
        return "Hello " + name + " from BridgeLabz";
    }

    // UC4: POST with Body
    @PostMapping("/post")
    public String helloWithBody(@RequestBody NameRequest nameRequest) {
        return "Hello " + nameRequest.getFirstName() + " " + nameRequest.getLastName() + " from BridgeLabz";
    }

    // UC5: PUT with Path Variable and Query Parameter
    @PutMapping("/put/{firstName}")
    public String helloWithPut(@PathVariable String firstName, @RequestParam String lastName) {
        return "Hello " + firstName + " " + lastName + " from BridgeLabz";
    }
}

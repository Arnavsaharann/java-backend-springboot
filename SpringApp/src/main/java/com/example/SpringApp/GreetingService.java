package com.example.SpringApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    // UC2: Get a simple greeting
    public String getGreeting() {
        return "Hello World";
    }

    // UC3: Get a custom greeting based on first and last name
    public String getCustomGreeting(NameRequest user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if (firstName != null && lastName != null) {
            return "Hello " + firstName + " " + lastName;
        } else if (firstName != null) {
            return "Hello " + firstName;
        } else if (lastName != null) {
            return "Hello " + lastName;
        } else {
            return getGreeting(); // Default to "Hello World"
        }
    }

    // UC4: Save a greeting message
    public GreetingDetails saveGreeting(String message) {
        GreetingDetails greetingDetails = new GreetingDetails(null, message);
        return greetingRepository.save(greetingDetails);
    }

    // UC5: Find a greeting by ID
    public Optional<GreetingDetails> findGreetingById(Long id) {
        return greetingRepository.findById(id);
    }

    // UC6: List all greetings
    public List<GreetingDetails> findAllGreetings() {
        return greetingRepository.findAll();
    }

    // UC7: Edit a greeting
    public GreetingDetails updateGreeting(Long id, String newMessage) {
        Optional<GreetingDetails> existingGreeting = greetingRepository.findById(id);
        if (existingGreeting.isPresent()) {
            GreetingDetails greetingDetails = existingGreeting.get();
            greetingDetails.setMessage(newMessage);
            return greetingRepository.save(greetingDetails);
        } else {
            throw new RuntimeException("Greeting with ID " + id + " not found");
        }
    }

    // UC8: Delete a greeting
    public void deleteGreeting(Long id) {
        if (greetingRepository.findById(id).isPresent()) {
            greetingRepository.delete(id);
        } else {
            throw new RuntimeException("Greeting with ID " + id + " not found");
        }
    }
}
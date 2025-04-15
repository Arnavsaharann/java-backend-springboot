package com.example.SpringApp;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GreetingRepository {
    private final Map<Long, GreetingDetails> greetings = new HashMap<>();
    private Long idCounter = 1L;

    public GreetingDetails save(GreetingDetails greetingDetails) {
        if (greetingDetails.getId() == null) {
            greetingDetails.setId(idCounter++);
        }
        greetings.put(greetingDetails.getId(), greetingDetails);
        return greetingDetails;
    }

    public Optional<GreetingDetails> findById(Long id) {
        return Optional.ofNullable(greetings.get(id));
    }

    public List<GreetingDetails> findAll() {
        return new ArrayList<>(greetings.values());
    }

    public void delete(Long id) {
        greetings.remove(id);
    }
}
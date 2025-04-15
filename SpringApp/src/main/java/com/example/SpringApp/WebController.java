package com.example.SpringApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/web/bridgelabz")
    public String showGreeting(Model model) {
        model.addAttribute("message", "Hello from BridgeLabz");
        return "hello"; // Matches hello.html
    }
}
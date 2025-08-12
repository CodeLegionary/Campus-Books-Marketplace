package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ContentController {
    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @GetMapping("/req/login")
public String login(org.springframework.ui.Model model) {
    model.addAttribute("frontendUrl", frontendUrl);
    return "login";
}

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/index")
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontendUrl); // Redirect correctly to React frontend
    }
}
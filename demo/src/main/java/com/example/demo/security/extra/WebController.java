package com.example.demo.security.extra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @GetMapping("/")
    public String redirectToReact() {
        return "redirect:" + frontendUrl;
    }
}
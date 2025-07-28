// src/main/java/com/example/demo/controller/ReportController.java
package com.example.demo.controller;

import com.example.demo.model.Report;
import com.example.demo.model.ReportRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // For @PreAuthorize
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports") // Base path for report-related endpoints
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;
    /**
     * Handles the submission of a simple user/book report.
     * Accessible by any authenticated user.
     *
     * @param reportPayload A map containing reportedUserId and reportedBookId.
     * @return A ResponseEntity indicating success or failure.
     */
    @PostMapping("/simple") // This maps to POST /api/reports/simple
    @PreAuthorize("isAuthenticated()") // Only accessible if a user is logged in
    @Transactional // Ensure both saving report and updating user are in one transaction
    public ResponseEntity<Map<String, String>> submitSimpleReport(@RequestBody Map<String, Long> reportPayload) {
        Long reportedUserId = reportPayload.get("reportedUserId");
        Long reportedBookId = reportPayload.get("reportedBookId");

        if (reportedUserId == null || reportedBookId == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User ID and Book ID are required for reporting.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            // 1. Save the report itself (as before)
            Report report = new Report(reportedUserId, reportedBookId);
            reportRepository.save(report);

            // 2. Flag the reported user
            Optional<User> reportedUserOptional = userRepository.findById(reportedUserId);
            if (reportedUserOptional.isPresent()) {
                User reportedUser = reportedUserOptional.get();
                reportedUser.setReported(true); // Set the flag to true
                userRepository.save(reportedUser); // Save the updated user
            } else {
                // Handle case where reported user ID doesn't exist (optional, but good for logs)
                System.err.println("Report submitted for non-existent user ID: " + reportedUserId);
                // You might still return success for the report itself, or a warning
            }

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Segnalazione inviata con successo e utente marcato!");
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Error processing report and flagging user: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Si è verificato un errore durante l'invio della segnalazione e il flag dell'utente.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // You can add more endpoints here later, e.g., to get all reports (for admin),
    // or to get reports by a specific user/book.
    @GetMapping("/all") // Maps to GET /api/reports/all
    @PreAuthorize("hasRole('ADMIN')") // Only accessible by users with ADMIN role
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }
}
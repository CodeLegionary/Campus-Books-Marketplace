package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports") // Good practice to explicitly name your table
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reportedUserId;  // The ID of the user being reported
    private Long reportedBookId;  // The ID of the book related to the report
    private LocalDateTime reportDate; // When the report was submitted

    // You might add more fields later, like:
    // private String reporterEmail; // (if you want to store who reported)
    // private String description;   // (if users can type a reason)
    // private String status;        // (e.g., "PENDING", "REVIEWED", "RESOLVED")

    // Constructors
    public Report() {
        this.reportDate = LocalDateTime.now(); // Automatically set creation time
    }

    public Report(Long reportedUserId, Long reportedBookId) {
        this(); // Call default constructor to set reportDate
        this.reportedUserId = reportedUserId;
        this.reportedBookId = reportedBookId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Long getReportedBookId() {
        return reportedBookId;
    }

    public void setReportedBookId(Long reportedBookId) {
        this.reportedBookId = reportedBookId;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }
}
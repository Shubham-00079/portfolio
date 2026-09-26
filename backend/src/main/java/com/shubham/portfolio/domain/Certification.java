package com.shubham.portfolio.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 240)
    private String title;
    @Column(nullable = false, length = 240)
    private String issuer;
    @Column(name = "issue_date", columnDefinition = "date")
    private LocalDate issueDate;
    @Column(length = 3000)
    private String description;
    @Column(name = "credential_url", length = 1000)
    private String credentialUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String credentialUrl) { this.credentialUrl = credentialUrl; }
}

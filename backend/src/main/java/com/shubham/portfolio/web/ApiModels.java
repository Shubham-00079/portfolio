package com.shubham.portfolio.web;

import com.shubham.portfolio.domain.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public final class ApiModels {
    private ApiModels() {}

    public record LoginInput(
        @NotBlank @Email @Size(max = 320) String email,
        @NotBlank @Size(max = 200) String password
    ) {}

    public record LoginView(String token, String type, String message) {}

    public record AboutInput(
        @NotBlank @Size(max = 255) String fullName,
        @NotBlank @Size(max = 255) String title,
        @Size(max = 3000) String summary,
        @Email @Size(max = 255) String email,
        @Size(max = 255) String phone,
        @Size(max = 255) String location,
        @Size(max = 255) String githubUrl,
        @Size(max = 255) String linkedinUrl,
        @Size(max = 255) String profileImage,
        @Size(max = 255) String resumeUrl
    ) {}

    public record AboutView(
        Long id, String fullName, String title, String summary, String email,
        String phone, String location, String githubUrl, String linkedinUrl,
        String profileImage, String resumeUrl
    ) {
        public static AboutView from(About value) {
            return new AboutView(value.getId(), value.getFullName(), value.getTitle(),
                value.getSummary(), value.getEmail(), value.getPhone(), value.getLocation(),
                value.getGithubUrl(), value.getLinkedinUrl(), value.getProfileImage(),
                value.getResumeUrl());
        }
    }

    public record ProjectInput(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 4000) String description,
        @NotBlank @Size(max = 255) String technology,
        @Size(max = 255) String githubUrl,
        @Size(max = 255) String liveUrl,
        @Size(max = 255) String imageUrl,
        Boolean featured
    ) {}

    public record ProjectView(
        Long id, String title, String description, String technology,
        String githubUrl, String liveUrl, String imageUrl, Boolean featured
    ) {
        public static ProjectView from(Project value) {
            return new ProjectView(value.getId(), value.getTitle(), value.getDescription(),
                value.getTechnology(), value.getGithubUrl(), value.getLiveUrl(),
                value.getImageUrl(), value.getFeatured());
        }
    }

    public record SkillInput(
        @NotBlank @Size(max = 255) String name,
        @Min(0) @Max(100) Integer percentage,
        @Size(max = 255) String icon,
        @Size(max = 255) String category
    ) {}

    public record SkillView(Long id, String name, Integer percentage, String icon, String category) {
        public static SkillView from(Skill value) {
            return new SkillView(value.getId(), value.getName(), value.getPercentage(),
                value.getIcon(), value.getCategory());
        }
    }

    public record ExperienceInput(
        @NotBlank @Size(max = 255) String company,
        @NotBlank @Size(max = 255) String designation,
        @Size(max = 255) String employmentType,
        @Size(max = 255) String location,
        LocalDate startDate,
        LocalDate endDate,
        Boolean currentlyWorking,
        @Size(max = 3000) String description,
        @Size(max = 255) String technologies
    ) {}

    public record ExperienceView(
        Long id, String company, String designation, String employmentType, String location,
        LocalDate startDate, LocalDate endDate, Boolean currentlyWorking,
        String description, String technologies
    ) {
        public static ExperienceView from(Experience value) {
            return new ExperienceView(value.getId(), value.getCompany(), value.getDesignation(),
                value.getEmploymentType(), value.getLocation(), value.getStartDate(),
                value.getEndDate(), value.getCurrentlyWorking(), value.getDescription(),
                value.getTechnologies());
        }
    }

    public record EducationInput(
        @NotBlank @Size(max = 255) String degree,
        @NotBlank @Size(max = 255) String institution,
        @Size(max = 255) String fieldOfStudy,
        @Min(1800) @Max(2200) Integer startYear,
        @Min(1800) @Max(2200) Integer endYear,
        @Size(max = 255) String grade,
        @Size(max = 3000) String description
    ) {}

    public record EducationView(
        Long id, String degree, String institution, String fieldOfStudy,
        Integer startYear, Integer endYear, String grade, String description
    ) {
        public static EducationView from(Education value) {
            return new EducationView(value.getId(), value.getDegree(), value.getInstitution(),
                value.getFieldOfStudy(), value.getStartYear(), value.getEndYear(),
                value.getGrade(), value.getDescription());
        }
    }

    public record CertificationInput(
        @NotBlank @Size(max = 240) String title,
        @NotBlank @Size(max = 240) String issuer,
        LocalDate issueDate,
        @Size(max = 3000) String description,
        @Size(max = 1000) String credentialUrl
    ) {}

    public record CertificationView(
        Long id, String title, String issuer, LocalDate issueDate,
        String description, String credentialUrl
    ) {
        public static CertificationView from(Certification value) {
            return new CertificationView(value.getId(), value.getTitle(), value.getIssuer(),
                value.getIssueDate(), value.getDescription(), value.getCredentialUrl());
        }
    }

    public record DashboardView(
        long totalSkills, long totalProjects, long totalExperience,
        long totalEducation, long totalCertificates, long totalMessages
    ) {}
}

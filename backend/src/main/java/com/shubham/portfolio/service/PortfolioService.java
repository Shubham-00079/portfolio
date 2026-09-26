package com.shubham.portfolio.service;

import com.shubham.portfolio.domain.*;
import com.shubham.portfolio.repository.*;
import com.shubham.portfolio.web.ApiModels;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {
    private final AboutRepository abouts;
    private final ProjectRepository projects;
    private final SkillRepository skills;
    private final ExperienceRepository experiences;
    private final EducationRepository educations;
    private final CertificationRepository certifications;

    public PortfolioService(
        AboutRepository abouts,
        ProjectRepository projects,
        SkillRepository skills,
        ExperienceRepository experiences,
        EducationRepository educations,
        CertificationRepository certifications
    ) {
        this.abouts = abouts;
        this.projects = projects;
        this.skills = skills;
        this.experiences = experiences;
        this.educations = educations;
        this.certifications = certifications;
    }

    @Transactional(readOnly = true)
    public ApiModels.AboutView getAbout() {
        return abouts.findFirstByOrderByIdAsc().map(ApiModels.AboutView::from)
            .orElseThrow(() -> new ResourceNotFoundException("About profile"));
    }

    @Transactional
    public ApiModels.AboutView createAbout(ApiModels.AboutInput input) {
        if (abouts.count() > 0) throw new ConflictException("An About profile already exists. Update it instead.");
        About value = new About();
        apply(value, input);
        return ApiModels.AboutView.from(abouts.save(value));
    }

    @Transactional
    public ApiModels.AboutView updateAbout(long id, ApiModels.AboutInput input) {
        About value = abouts.findById(id).orElseThrow(() -> new ResourceNotFoundException("About profile"));
        apply(value, input);
        return ApiModels.AboutView.from(abouts.save(value));
    }

    @Transactional
    public void deleteAbout(long id) {
        abouts.delete(abouts.findById(id).orElseThrow(() -> new ResourceNotFoundException("About profile")));
    }

    @Transactional(readOnly = true)
    public List<ApiModels.ProjectView> getProjects() {
        return projects.findAllByOrderByIdDesc().stream().map(ApiModels.ProjectView::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ApiModels.ProjectView> getFeaturedProjects() {
        return projects.findAllByFeaturedTrueOrderByIdDesc().stream().map(ApiModels.ProjectView::from).toList();
    }

    @Transactional(readOnly = true)
    public ApiModels.ProjectView getProject(long id) {
        return ApiModels.ProjectView.from(projects.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project")));
    }

    @Transactional
    public ApiModels.ProjectView createProject(ApiModels.ProjectInput input) {
        Project value = new Project();
        apply(value, input);
        return ApiModels.ProjectView.from(projects.save(value));
    }

    @Transactional
    public ApiModels.ProjectView updateProject(long id, ApiModels.ProjectInput input) {
        Project value = projects.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project"));
        apply(value, input);
        return ApiModels.ProjectView.from(projects.save(value));
    }

    @Transactional
    public void deleteProject(long id) {
        projects.delete(projects.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project")));
    }

    @Transactional(readOnly = true)
    public List<ApiModels.SkillView> getSkills() {
        return skills.findAllByOrderByIdAsc().stream().map(ApiModels.SkillView::from).toList();
    }

    @Transactional
    public ApiModels.SkillView createSkill(ApiModels.SkillInput input) {
        Skill value = new Skill();
        apply(value, input);
        return ApiModels.SkillView.from(skills.save(value));
    }

    @Transactional
    public ApiModels.SkillView updateSkill(long id, ApiModels.SkillInput input) {
        Skill value = skills.findById(id).orElseThrow(() -> new ResourceNotFoundException("Skill"));
        apply(value, input);
        return ApiModels.SkillView.from(skills.save(value));
    }

    @Transactional
    public void deleteSkill(long id) {
        skills.delete(skills.findById(id).orElseThrow(() -> new ResourceNotFoundException("Skill")));
    }

    @Transactional(readOnly = true)
    public List<ApiModels.ExperienceView> getExperiences() {
        return experiences.findAllByOrderByIdDesc().stream().map(ApiModels.ExperienceView::from).toList();
    }

    @Transactional
    public ApiModels.ExperienceView createExperience(ApiModels.ExperienceInput input) {
        Experience value = new Experience();
        apply(value, input);
        return ApiModels.ExperienceView.from(experiences.save(value));
    }

    @Transactional
    public ApiModels.ExperienceView updateExperience(long id, ApiModels.ExperienceInput input) {
        Experience value = experiences.findById(id).orElseThrow(() -> new ResourceNotFoundException("Experience"));
        apply(value, input);
        return ApiModels.ExperienceView.from(experiences.save(value));
    }

    @Transactional
    public void deleteExperience(long id) {
        experiences.delete(experiences.findById(id).orElseThrow(() -> new ResourceNotFoundException("Experience")));
    }

    @Transactional(readOnly = true)
    public List<ApiModels.EducationView> getEducations() {
        return educations.findAllByOrderByIdDesc().stream().map(ApiModels.EducationView::from).toList();
    }

    @Transactional
    public ApiModels.EducationView createEducation(ApiModels.EducationInput input) {
        validateYears(input.startYear(), input.endYear());
        Education value = new Education();
        apply(value, input);
        return ApiModels.EducationView.from(educations.save(value));
    }

    @Transactional
    public ApiModels.EducationView updateEducation(long id, ApiModels.EducationInput input) {
        validateYears(input.startYear(), input.endYear());
        Education value = educations.findById(id).orElseThrow(() -> new ResourceNotFoundException("Education"));
        apply(value, input);
        return ApiModels.EducationView.from(educations.save(value));
    }

    @Transactional
    public void deleteEducation(long id) {
        educations.delete(educations.findById(id).orElseThrow(() -> new ResourceNotFoundException("Education")));
    }

    @Transactional(readOnly = true)
    public List<ApiModels.CertificationView> getCertifications() {
        return certifications.findAllByOrderByIdDesc().stream().map(ApiModels.CertificationView::from).toList();
    }

    @Transactional
    public ApiModels.CertificationView createCertification(ApiModels.CertificationInput input) {
        Certification value = new Certification();
        apply(value, input);
        return ApiModels.CertificationView.from(certifications.save(value));
    }

    @Transactional
    public ApiModels.CertificationView updateCertification(long id, ApiModels.CertificationInput input) {
        Certification value = certifications.findById(id).orElseThrow(() -> new ResourceNotFoundException("Certification"));
        apply(value, input);
        return ApiModels.CertificationView.from(certifications.save(value));
    }

    @Transactional
    public void deleteCertification(long id) {
        certifications.delete(certifications.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Certification")));
    }

    @Transactional(readOnly = true)
    public ApiModels.DashboardView getDashboard() {
        return new ApiModels.DashboardView(skills.count(), projects.count(), experiences.count(),
            educations.count(), certifications.count(), 0);
    }

    private void apply(About value, ApiModels.AboutInput input) {
        value.setFullName(input.fullName().trim());
        value.setTitle(input.title().trim());
        value.setSummary(clean(input.summary()));
        value.setEmail(clean(input.email()));
        value.setPhone(clean(input.phone()));
        value.setLocation(clean(input.location()));
        value.setGithubUrl(clean(input.githubUrl()));
        value.setLinkedinUrl(clean(input.linkedinUrl()));
        value.setProfileImage(clean(input.profileImage()));
        value.setResumeUrl(clean(input.resumeUrl()));
    }

    private void apply(Project value, ApiModels.ProjectInput input) {
        value.setTitle(input.title().trim());
        value.setDescription(input.description().trim());
        value.setTechnology(input.technology().trim());
        value.setGithubUrl(clean(input.githubUrl()));
        value.setLiveUrl(clean(input.liveUrl()));
        value.setImageUrl(clean(input.imageUrl()));
        value.setFeatured(Boolean.TRUE.equals(input.featured()));
    }

    private void apply(Skill value, ApiModels.SkillInput input) {
        value.setName(input.name().trim());
        value.setPercentage(input.percentage());
        value.setIcon(clean(input.icon()));
        value.setCategory(clean(input.category()));
    }

    private void apply(Experience value, ApiModels.ExperienceInput input) {
        if (input.startDate() != null && input.endDate() != null && input.endDate().isBefore(input.startDate())) {
            throw new IllegalArgumentException("End date must not be before start date.");
        }
        boolean current = Boolean.TRUE.equals(input.currentlyWorking());
        value.setCompany(input.company().trim());
        value.setDesignation(input.designation().trim());
        value.setEmploymentType(clean(input.employmentType()));
        value.setLocation(clean(input.location()));
        value.setStartDate(input.startDate());
        value.setEndDate(current ? null : input.endDate());
        value.setCurrentlyWorking(current);
        value.setDescription(clean(input.description()));
        value.setTechnologies(clean(input.technologies()));
    }

    private void apply(Education value, ApiModels.EducationInput input) {
        value.setDegree(input.degree().trim());
        value.setInstitution(input.institution().trim());
        value.setFieldOfStudy(clean(input.fieldOfStudy()));
        value.setStartYear(input.startYear());
        value.setEndYear(input.endYear());
        value.setGrade(clean(input.grade()));
        value.setDescription(clean(input.description()));
    }

    private void apply(Certification value, ApiModels.CertificationInput input) {
        value.setTitle(input.title().trim());
        value.setIssuer(input.issuer().trim());
        value.setIssueDate(input.issueDate());
        value.setDescription(clean(input.description()));
        value.setCredentialUrl(clean(input.credentialUrl()));
    }

    private void validateYears(Integer startYear, Integer endYear) {
        if (startYear != null && endYear != null && endYear < startYear) {
            throw new IllegalArgumentException("End year must not be before start year.");
        }
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}

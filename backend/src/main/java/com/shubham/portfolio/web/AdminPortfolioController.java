package com.shubham.portfolio.web;

import com.shubham.portfolio.service.PortfolioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminPortfolioController {
    private final PortfolioService portfolio;

    public AdminPortfolioController(PortfolioService portfolio) {
        this.portfolio = portfolio;
    }

    @GetMapping("/dashboard")
    public ApiModels.DashboardView dashboard() { return portfolio.getDashboard(); }

    @GetMapping("/about")
    public ApiModels.AboutView about() { return portfolio.getAbout(); }

    @PostMapping("/about")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.AboutView createAbout(@Valid @RequestBody ApiModels.AboutInput input) {
        return portfolio.createAbout(input);
    }

    @PutMapping("/about/{id}")
    public ApiModels.AboutView updateAbout(@PathVariable long id, @Valid @RequestBody ApiModels.AboutInput input) {
        return portfolio.updateAbout(id, input);
    }

    @DeleteMapping("/about/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbout(@PathVariable long id) { portfolio.deleteAbout(id); }

    @GetMapping("/projects")
    public List<ApiModels.ProjectView> projects() { return portfolio.getProjects(); }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.ProjectView createProject(@Valid @RequestBody ApiModels.ProjectInput input) {
        return portfolio.createProject(input);
    }

    @PutMapping("/projects/{id}")
    public ApiModels.ProjectView updateProject(@PathVariable long id, @Valid @RequestBody ApiModels.ProjectInput input) {
        return portfolio.updateProject(id, input);
    }

    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable long id) { portfolio.deleteProject(id); }

    @GetMapping("/skills")
    public List<ApiModels.SkillView> skills() { return portfolio.getSkills(); }

    @PostMapping("/skills")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.SkillView createSkill(@Valid @RequestBody ApiModels.SkillInput input) {
        return portfolio.createSkill(input);
    }

    @PutMapping("/skills/{id}")
    public ApiModels.SkillView updateSkill(@PathVariable long id, @Valid @RequestBody ApiModels.SkillInput input) {
        return portfolio.updateSkill(id, input);
    }

    @DeleteMapping("/skills/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable long id) { portfolio.deleteSkill(id); }

    @GetMapping("/experience")
    public List<ApiModels.ExperienceView> experience() { return portfolio.getExperiences(); }

    @PostMapping("/experience")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.ExperienceView createExperience(@Valid @RequestBody ApiModels.ExperienceInput input) {
        return portfolio.createExperience(input);
    }

    @PutMapping("/experience/{id}")
    public ApiModels.ExperienceView updateExperience(@PathVariable long id, @Valid @RequestBody ApiModels.ExperienceInput input) {
        return portfolio.updateExperience(id, input);
    }

    @DeleteMapping("/experience/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExperience(@PathVariable long id) { portfolio.deleteExperience(id); }

    @GetMapping("/education")
    public List<ApiModels.EducationView> education() { return portfolio.getEducations(); }

    @PostMapping("/education")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.EducationView createEducation(@Valid @RequestBody ApiModels.EducationInput input) {
        return portfolio.createEducation(input);
    }

    @PutMapping("/education/{id}")
    public ApiModels.EducationView updateEducation(@PathVariable long id, @Valid @RequestBody ApiModels.EducationInput input) {
        return portfolio.updateEducation(id, input);
    }

    @DeleteMapping("/education/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEducation(@PathVariable long id) { portfolio.deleteEducation(id); }

    @GetMapping("/certifications")
    public List<ApiModels.CertificationView> certifications() { return portfolio.getCertifications(); }

    @PostMapping("/certifications")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiModels.CertificationView createCertification(@Valid @RequestBody ApiModels.CertificationInput input) {
        return portfolio.createCertification(input);
    }

    @PutMapping("/certifications/{id}")
    public ApiModels.CertificationView updateCertification(
        @PathVariable long id,
        @Valid @RequestBody ApiModels.CertificationInput input
    ) {
        return portfolio.updateCertification(id, input);
    }

    @DeleteMapping("/certifications/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertification(@PathVariable long id) { portfolio.deleteCertification(id); }
}

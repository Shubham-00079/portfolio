package com.shubham.portfolio.web;

import com.shubham.portfolio.service.PortfolioService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicPortfolioController {
    private final PortfolioService portfolio;

    public PublicPortfolioController(PortfolioService portfolio) {
        this.portfolio = portfolio;
    }

    @GetMapping("/about")
    public ApiModels.AboutView about() { return portfolio.getAbout(); }

    @GetMapping("/projects")
    public List<ApiModels.ProjectView> projects() { return portfolio.getProjects(); }

    @GetMapping("/projects/featured")
    public List<ApiModels.ProjectView> featuredProjects() { return portfolio.getFeaturedProjects(); }

    @GetMapping("/projects/{id}")
    public ApiModels.ProjectView project(@PathVariable long id) { return portfolio.getProject(id); }

    @GetMapping("/skills")
    public List<ApiModels.SkillView> skills() { return portfolio.getSkills(); }

    @GetMapping("/experience")
    public List<ApiModels.ExperienceView> experience() { return portfolio.getExperiences(); }

    @GetMapping("/education")
    public List<ApiModels.EducationView> education() { return portfolio.getEducations(); }

    @GetMapping("/certifications")
    public List<ApiModels.CertificationView> certifications() { return portfolio.getCertifications(); }
}

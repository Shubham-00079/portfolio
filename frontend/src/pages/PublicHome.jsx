import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/client";

const fallbackAbout = {
  fullName: "Shubham Kumar",
  title: "JAVA FULL-STACK DEV.",
  summary: "Java Full Stack Developer specializing in building modern, scalable web applications using Java, Spring Boot, React, REST APIs and MySQL.",
  email: "",
  phone: "",
  location: "",
  githubUrl: "",
  linkedinUrl: "",
  profileImage: "",
  resumeUrl: "",
};

function safeWebUrl(value) {
  if (!value) return "";
  try {
    const url = new URL(value, window.location.origin);
    return url.protocol === "https:" || url.protocol === "http:" ? url.href : "";
  } catch {
    return "";
  }
}

function initials(name) {
  return (name || "SK").trim().split(/\s+/).slice(0, 2).map((part) => part[0] || "").join("").toUpperCase();
}

function dateLabel(value) {
  if (!value) return "";
  const date = new Date(value.length === 10 ? value + "T12:00:00" : value);
  if (Number.isNaN(date.getTime())) return value;
  return new Intl.DateTimeFormat(undefined, { year: "numeric", month: "short" }).format(date);
}

function dateRange(start, end, current) {
  const first = dateLabel(start);
  const last = current ? "Present" : dateLabel(end);
  return [first, last].filter(Boolean).join(" — ");
}

function SectionHeading({ eyebrow, title, note }) {
  return (
    <div className="section-heading">
      <p className="eyebrow">{eyebrow}</p>
      <h2>{title}</h2>
      {note && <p className="section-note">{note}</p>}
    </div>
  );
}

function PublicHome() {
  const [about, setAbout] = useState(fallbackAbout);
  const [skills, setSkills] = useState([]);
  const [projects, setProjects] = useState([]);
  const [experience, setExperience] = useState([]);
  const [education, setEducation] = useState([]);
  const [certifications, setCertifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let mounted = true;
    Promise.allSettled([
      api.get("/public/about").then((result) => result.data),
      api.get("/public/skills").then((result) => result.data),
      api.get("/public/projects").then((result) => result.data),
      api.get("/public/experience").then((result) => result.data),
      api.get("/public/education").then((result) => result.data),
      api.get("/public/certifications").then((result) => result.data),
    ]).then((results) => {
      if (!mounted) return;
      const [profile, skillRows, projectRows, experienceRows, educationRows, certificateRows] = results;
      if (profile.status === "fulfilled" && profile.value) setAbout((current) => ({ ...current, ...profile.value }));
      if (skillRows.status === "fulfilled" && Array.isArray(skillRows.value)) setSkills(skillRows.value);
      if (projectRows.status === "fulfilled" && Array.isArray(projectRows.value)) setProjects(projectRows.value);
      if (experienceRows.status === "fulfilled" && Array.isArray(experienceRows.value)) setExperience(experienceRows.value);
      if (educationRows.status === "fulfilled" && Array.isArray(educationRows.value)) setEducation(educationRows.value);
      if (certificateRows.status === "fulfilled" && Array.isArray(certificateRows.value)) setCertifications(certificateRows.value);
      setLoading(false);
    });
    return () => { mounted = false; };
  }, []);

  const displayName = about.fullName || fallbackAbout.fullName;
  const portrait = safeWebUrl(about.profileImage);
  const resume = safeWebUrl(about.resumeUrl);
  const socialLinks = [
    ["GitHub", about.githubUrl],
    ["LinkedIn", about.linkedinUrl],
  ].map(([label, value]) => ({ label, href: safeWebUrl(value) })).filter((item) => item.href);

  return (
    <div className="public-site">
      <header className="site-header">
        <a className="site-brand" href="#home" aria-label="Back to top">
          <span className="brand-mark">SK</span>
          <span>{displayName}</span>
        </a>
        <nav className="site-nav" aria-label="Main navigation">
          <a href="#about">About</a>
          <a href="#skills">Skills</a>
          <a href="#experience">Experience</a>
          <a href="#education">Education</a>
          <a href="#projects">Projects</a>
          <a href="#certifications">Certifications</a>
          <a href="#contact">Contact</a>
        </nav>
        <Link className="admin-entry" to="/login">Admin <span aria-hidden="true">↗</span></Link>
      </header>

      <main>
        <section id="home" className="hero-section">
          <div className="hero-orbit hero-orbit-one" />
          <div className="hero-orbit hero-orbit-two" />
          <div className="hero-content">
            <p className="availability"><span /> AVAILABLE FOR NEW OPPORTUNITIES</p>
            <p className="hero-intro">Hello, I’m {displayName}</p>
            <h1>Building modern<br /><em>digital experiences.</em></h1>
            <p className="hero-title">{about.title || fallbackAbout.title}</p>
            <p className="hero-summary">{about.summary || fallbackAbout.summary}</p>
            <div className="hero-actions">
              <a className="button button-bright" href={projects.length ? "#projects" : "#about"}>View my work <span aria-hidden="true">↘</span></a>
              {resume
                ? <a className="button button-outline-light" href={resume} target="_blank" rel="noreferrer">View resume ↗</a>
                : <a className="button button-outline-light" href="#contact">Let’s connect ↘</a>}
            </div>
            <div className="hero-tech" aria-label="Core technologies">
              {["Java", "Spring Boot", "React", "REST API", "MySQL"].map((item) => <span key={item}>{item}</span>)}
            </div>
          </div>
          <div className="hero-portrait" aria-label={displayName}>
            {portrait ? <img src={portrait} alt={displayName} /> : <span>{initials(displayName)}</span>}
            <div className="portrait-caption"><span className="status-dot" /> JAVA · SPRING · REACT</div>
          </div>
          <a className="scroll-cue" href="#about">SCROLL TO EXPLORE <span>↓</span></a>
        </section>

        <section id="about" className="content-section">
          <SectionHeading eyebrow="INTRODUCTION" title="A little about me" />
          <div className="about-grid">
            <article className="about-copy panel">
              <p className="eyebrow">{displayName}</p>
              <h3>{about.title || "Java Full-Stack Developer"}</h3>
              <p>{about.summary || "Add your About content from the admin panel to introduce yourself here."}</p>
              {socialLinks.length > 0 && (
                <div className="inline-links">
                  {socialLinks.map((item) => <a key={item.label} href={item.href} target="_blank" rel="noreferrer">{item.label} ↗</a>)}
                  {resume && <a href={resume} target="_blank" rel="noreferrer">Resume ↗</a>}
                </div>
              )}
            </article>
            <aside className="about-facts panel">
              <p className="eyebrow">PROFILE</p>
              {about.location && <div className="fact"><span>Location</span><strong>{about.location}</strong></div>}
              {about.email && <div className="fact"><span>Email</span><a href={"mailto:" + about.email}>{about.email}</a></div>}
              {about.phone && <div className="fact"><span>Phone</span><a href={"tel:" + about.phone.replace(/[^\d+]/g, "")}>{about.phone}</a></div>}
              {!about.location && !about.email && !about.phone && <p className="empty-inline">Contact details can be added in the admin workspace.</p>}
            </aside>
          </div>
        </section>

        <section id="skills" className="content-section section-soft">
          <SectionHeading eyebrow="MY EXPERTISE" title="Skills" note="Technologies and tools used across my work." />
          {skills.length ? (
            <div className="skills-grid">
              {skills.map((skill) => {
                const percentage = Math.max(0, Math.min(100, Number(skill.percentage) || 0));
                return (
                  <article className="skill-card panel" key={skill.id || skill.name}>
                    <div className="skill-heading"><div><h3>{skill.name}</h3><p>{skill.category || "Technology"}</p></div><strong>{skill.percentage == null ? "" : skill.percentage + "%"}</strong></div>
                    <div className="skill-track" aria-hidden="true"><span style={{ width: percentage + "%" }} /></div>
                  </article>
                );
              })}
            </div>
          ) : <p className="empty-state">{loading ? "Loading skills…" : "No skills are available yet."}</p>}
        </section>

        <section id="experience" className="content-section">
          <SectionHeading eyebrow="CAREER PATH" title="Experience" />
          {experience.length ? (
            <div className="timeline">
              {experience.map((item) => (
                <article className="timeline-item panel" key={item.id}>
                  <div className="timeline-marker" />
                  <div className="timeline-top"><div><h3>{item.designation}</h3><p className="timeline-subtitle">{item.company}{item.location ? " · " + item.location : ""}</p></div><span className="timeline-date">{dateRange(item.startDate, item.endDate, item.currentlyWorking)}</span></div>
                  {item.employmentType && <p className="eyebrow">{item.employmentType}</p>}
                  {item.description && <p>{item.description}</p>}
                  {item.technologies && <p className="muted-copy">Technologies: {item.technologies}</p>}
                </article>
              ))}
            </div>
          ) : <p className="empty-state">{loading ? "Loading experience…" : "No experience has been added yet."}</p>}
        </section>

        <section id="education" className="content-section section-soft">
          <SectionHeading eyebrow="LEARNING" title="Education" />
          {education.length ? (
            <div className="record-grid">
              {education.map((item) => (
                <article className="panel record-card" key={item.id}>
                  <span className="record-range">{[item.startYear, item.endYear].filter(Boolean).join(" — ")}</span>
                  <h3>{item.degree}</h3>
                  <p className="timeline-subtitle">{item.institution}{item.fieldOfStudy ? " · " + item.fieldOfStudy : ""}</p>
                  {item.grade && <p className="muted-copy">{item.grade}</p>}
                  {item.description && <p>{item.description}</p>}
                </article>
              ))}
            </div>
          ) : <p className="empty-state">{loading ? "Loading education…" : "No education details have been added yet."}</p>}
        </section>

        <section id="projects" className="content-section">
          <SectionHeading eyebrow="SELECTED WORK" title="Projects" note="A selection of projects and the technologies behind them." />
          {projects.length ? (
            <div className="project-grid">
              {projects.map((project) => {
                const image = safeWebUrl(project.imageUrl);
                const github = safeWebUrl(project.githubUrl);
                const live = safeWebUrl(project.liveUrl);
                const tech = (project.technology || "").split(/[,\n]/).map((item) => item.trim()).filter(Boolean);
                return (
                  <article className="project-card panel" key={project.id}>
                    <div className="project-art">
                      {image ? <img src={image} alt="" loading="lazy" /> : <div className="project-art-placeholder"><span>{initials(project.title)}</span></div>}
                      {project.featured && <span className="featured-badge">FEATURED</span>}
                    </div>
                    <div className="project-body">
                      <p className="eyebrow">PROJECT</p>
                      <h3>{project.title}</h3>
                      <p>{project.description}</p>
                      <div className="tag-list">{tech.map((item) => <span className="tag" key={item}>{item}</span>)}</div>
                      <div className="project-links">
                        {github && <a href={github} target="_blank" rel="noreferrer">Source code ↗</a>}
                        {live && <a href={live} target="_blank" rel="noreferrer">Live project ↗</a>}
                      </div>
                    </div>
                  </article>
                );
              })}
            </div>
          ) : <p className="empty-state">{loading ? "Loading projects…" : "No projects are available yet."}</p>}
        </section>

        <section id="certifications" className="content-section section-soft">
          <SectionHeading eyebrow="PROFESSIONAL GROWTH" title="Certifications" />
          {certifications.length ? (
            <div className="record-grid">
              {certifications.map((item) => {
                const credential = safeWebUrl(item.credentialUrl);
                return (
                  <article className="panel record-card" key={item.id}>
                    <span className="record-range">{dateLabel(item.issueDate)}</span>
                    <h3>{item.title}</h3>
                    <p className="timeline-subtitle">{item.issuer}</p>
                    {item.description && <p>{item.description}</p>}
                    {credential && <a className="text-link" href={credential} target="_blank" rel="noreferrer">View credential ↗</a>}
                  </article>
                );
              })}
            </div>
          ) : <p className="empty-state">{loading ? "Loading certifications…" : "No certifications are available yet."}</p>}
        </section>

        <section id="contact" className="content-section contact-section">
          <SectionHeading eyebrow="GET IN TOUCH" title="Let’s connect" note="For conversations about projects, work, or collaboration." />
          <div className="contact-grid">
            <div className="contact-intro">
              <p>Have a project in mind? Reach out through email or connect on my professional profiles.</p>
              {about.email && <a className="button button-primary" href={"mailto:" + about.email}>Send an email <span aria-hidden="true">↗</span></a>}
            </div>
            <div className="panel contact-details">
              {about.email && <div className="fact"><span>Email</span><a href={"mailto:" + about.email}>{about.email}</a></div>}
              {about.phone && <div className="fact"><span>Phone</span><a href={"tel:" + about.phone.replace(/[^\d+]/g, "")}>{about.phone}</a></div>}
              {about.location && <div className="fact"><span>Location</span><strong>{about.location}</strong></div>}
              {socialLinks.map((item) => <div className="fact" key={item.label}><span>{item.label}</span><a href={item.href} target="_blank" rel="noreferrer">Visit profile ↗</a></div>)}
              {!about.email && !about.phone && !about.location && socialLinks.length === 0 && <p className="empty-inline">Contact details can be added in the admin workspace.</p>}
            </div>
          </div>
        </section>
      </main>
      <footer className="site-footer"><a className="site-brand" href="#home"><span className="brand-mark">SK</span><span>{displayName}</span></a><span>Designed and built with care · {new Date().getFullYear()}</span><a href="#home">Back to top ↑</a></footer>
    </div>
  );
}

export default PublicHome;

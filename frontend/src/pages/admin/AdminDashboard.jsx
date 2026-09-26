import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/client";

const groups = [
  ["totalProjects", "Projects", "Published work"],
  ["totalSkills", "Skills", "Technical strengths"],
  ["totalExperience", "Experience", "Career entries"],
  ["totalEducation", "Education", "Education records"],
  ["totalCertificates", "Certifications", "Professional learning"],
];

export default function AdminDashboard() {
  const [summary, setSummary] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/admin/dashboard")
      .then((response) => setSummary(response.data))
      .catch(() => setError("Dashboard counts are unavailable right now."));
  }, []);

  return (
    <div className="admin-page">
      <div className="page-heading">
        <div><p className="eyebrow">OVERVIEW</p><h1>Dashboard</h1><p>Manage the content shown on your public portfolio.</p></div>
        <Link className="button button-primary" to="/admin/about">Edit profile</Link>
      </div>
      {error && <p className="form-alert" role="status">{error}</p>}
      <div className="metric-grid">
        {groups.map(([key, label, hint]) => (
          <article className="metric-card" key={key}>
            <span>{label}</span><strong>{summary ? summary[key] : "—"}</strong><small>{hint}</small>
          </article>
        ))}
      </div>
      <section className="admin-shortcuts">
        <div className="section-heading compact"><p className="eyebrow">QUICK ACCESS</p><h2>Portfolio content</h2></div>
        <div className="shortcut-grid">
          {[
            ["/admin/projects", "Projects", "Add work samples and mark featured projects."],
            ["/admin/skills", "Skills", "Manage skill names, categories, and levels."],
            ["/admin/experience", "Experience", "Keep role dates and descriptions current."],
            ["/admin/education", "Education", "Add schools, degrees, and dates."],
            ["/admin/certifications", "Certifications", "Manage credentials and verification links."],
          ].map(([to, label, hint]) => (
            <Link className="shortcut-card" to={to} key={to}><strong>{label} <span aria-hidden="true">↗</span></strong><span>{hint}</span></Link>
          ))}
        </div>
      </section>
    </div>
  );
}

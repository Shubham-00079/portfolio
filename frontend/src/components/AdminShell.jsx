import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

const navItems = [
  ["dashboard", "Overview"],
  ["about", "About profile"],
  ["projects", "Projects"],
  ["skills", "Skills"],
  ["experience", "Experience"],
  ["education", "Education"],
  ["certifications", "Certifications"],
];

export default function AdminShell() {
  const { signOut } = useAuth();
  const navigate = useNavigate();

  function logout() {
    signOut();
    navigate("/login", { replace: true });
  }

  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <a className="admin-brand" href="/">
          <span className="brand-mark">SK</span>
          <span><strong>Portfolio</strong><small>Admin workspace</small></span>
        </a>
        <nav className="admin-nav" aria-label="Admin navigation">
          <span className="nav-label">MANAGE</span>
          {navItems.map(([path, label]) => (
            <NavLink
              key={path}
              to={"/admin/" + path}
              className={({ isActive }) => "admin-nav-link" + (isActive ? " active" : "")}
            >
              {label}
            </NavLink>
          ))}
        </nav>
        <div className="sidebar-bottom">
          <a href="/" className="admin-nav-link">View public site <span aria-hidden="true">↗</span></a>
          <button className="admin-nav-link logout-link" type="button" onClick={logout}>Sign out</button>
        </div>
      </aside>
      <div className="admin-main">
        <header className="admin-topbar">
          <span>Portfolio management</span>
          <a href="/" target="_blank" rel="noreferrer">Open portfolio ↗</a>
        </header>
        <main className="admin-content"><Outlet /></main>
      </div>
    </div>
  );
}

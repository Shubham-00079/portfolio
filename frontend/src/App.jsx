import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminShell from "./components/AdminShell";
import PublicHome from "./pages/PublicHome";
import LoginPage from "./pages/LoginPage";
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminAbout from "./pages/admin/AdminAbout";
import AdminProjects from "./pages/admin/AdminProjects";
import AdminSkills from "./pages/admin/AdminSkills";
import AdminExperience from "./pages/admin/AdminExperience";
import AdminEducation from "./pages/admin/AdminEducation";
import AdminCertifications from "./pages/admin/AdminCertifications";

function NotFound() {
  return (
    <main className="not-found">
      <p className="eyebrow">404 · PAGE NOT FOUND</p>
      <h1>That page is not here.</h1>
      <a className="button button-primary" href="/">Back to portfolio</a>
    </main>
  );
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<PublicHome />} />
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/admin"
        element={
          <ProtectedRoute>
            <AdminShell />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="dashboard" replace />} />
        <Route path="dashboard" element={<AdminDashboard />} />
        <Route path="about" element={<AdminAbout />} />
        <Route path="projects" element={<AdminProjects />} />
        <Route path="skills" element={<AdminSkills />} />
        <Route path="experience" element={<AdminExperience />} />
        <Route path="education" element={<AdminEducation />} />
        <Route path="certifications" element={<AdminCertifications />} />
      </Route>
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

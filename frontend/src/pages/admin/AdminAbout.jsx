import { useEffect, useState } from "react";
import api from "../../api/client";

const emptyAbout = {
  fullName: "", title: "", summary: "", email: "", phone: "", location: "",
  githubUrl: "", linkedinUrl: "", profileImage: "", resumeUrl: "",
};

const fields = [
  ["fullName", "Full name", "text", true],
  ["title", "Professional title", "text", true],
  ["email", "Email", "email", false],
  ["phone", "Phone", "tel", false],
  ["location", "Location", "text", false],
  ["githubUrl", "GitHub URL", "url", false],
  ["linkedinUrl", "LinkedIn URL", "url", false],
  ["profileImage", "Profile image URL", "url", false],
  ["resumeUrl", "Resume URL", "url", false],
];

export default function AdminAbout() {
  const [profile, setProfile] = useState(emptyAbout);
  const [profileId, setProfileId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");

  useEffect(() => {
    api.get("/admin/about")
      .then((response) => {
        setProfileId(response.data.id);
        setProfile({ ...emptyAbout, ...response.data });
      })
      .catch((requestError) => {
        if (!requestError.response || requestError.response.status !== 404) {
          setError("Profile details could not be loaded.");
        }
      })
      .finally(() => setLoading(false));
  }, []);

  function change(name, value) {
    setProfile((current) => ({ ...current, [name]: value }));
  }

  async function submit(event) {
    event.preventDefault();
    setBusy(true);
    setError("");
    setNotice("");
    try {
      const response = profileId
        ? await api.put("/admin/about/" + profileId, profile)
        : await api.post("/admin/about", profile);
      setProfileId(response.data.id);
      setNotice("Profile saved. Your public site will show the updated details.");
    } catch (requestError) {
      setError((requestError.response && requestError.response.data && requestError.response.data.error) || "Profile could not be saved.");
    } finally {
      setBusy(false);
    }
  }

  async function remove() {
    if (!profileId || !window.confirm("Delete the About profile from the public portfolio?")) return;
    setBusy(true);
    setError("");
    try {
      await api.delete("/admin/about/" + profileId);
      setProfile(emptyAbout);
      setProfileId(null);
      setNotice("Profile removed.");
    } catch {
      setError("Profile could not be removed.");
    } finally {
      setBusy(false);
    }
  }

  if (loading) return <div className="admin-page"><p>Loading profile…</p></div>;

  return (
    <div className="admin-page">
      <div className="page-heading"><div><p className="eyebrow">PUBLIC PROFILE</p><h1>About</h1><p>Manage your introduction, contact details, and profile links.</p></div></div>
      <form className="admin-form panel" onSubmit={submit}>
        <div className="field-grid">
          {fields.map(([name, label, type, required]) => (
            <label className="field" key={name}>
              <span>{label}</span>
              <input type={type} name={name} value={profile[name] || ""} required={required} maxLength={255} onChange={(event) => change(name, event.target.value)} />
            </label>
          ))}
          <label className="field field-full"><span>Summary</span><textarea name="summary" rows="6" maxLength="3000" value={profile.summary || ""} onChange={(event) => change("summary", event.target.value)} /></label>
        </div>
        {error && <p className="form-alert" role="alert">{error}</p>}
        {notice && <p className="form-success" role="status">{notice}</p>}
        <div className="form-actions">
          <button className="button button-primary" type="submit" disabled={busy}>{busy ? "Saving…" : "Save profile"}</button>
          {profileId && <button className="button button-danger-outline" type="button" onClick={remove} disabled={busy}>Remove profile</button>}
        </div>
      </form>
    </div>
  );
}

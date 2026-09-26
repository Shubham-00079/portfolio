import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function LoginPage() {
  const { signIn } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);

  async function submit(event) {
    event.preventDefault();
    setError("");
    setBusy(true);
    try {
      await signIn(email.trim(), password);
      const destination = location.state && location.state.from && location.state.from.startsWith("/admin")
        ? location.state.from
        : "/admin/dashboard";
      navigate(destination, { replace: true });
    } catch {
      setError("Sign in failed. Check your email and password.");
    } finally {
      setBusy(false);
    }
  }

  return (
    <main className="login-page">
      <div className="login-glow" />
      <section className="login-card">
        <a className="site-brand login-brand" href="/">
          <span className="brand-mark">SK</span><span>Portfolio</span>
        </a>
        <p className="eyebrow">ADMIN WORKSPACE</p>
        <h1>Welcome back.</h1>
        <p className="login-copy">Sign in to update your portfolio profile, projects, and experience.</p>
        <form onSubmit={submit} className="form-stack">
          <label className="field">
            <span>Email</span>
            <input type="email" name="email" autoComplete="username" required maxLength="320" value={email} onChange={(event) => setEmail(event.target.value)} />
          </label>
          <label className="field">
            <span>Password</span>
            <input type="password" name="password" autoComplete="current-password" required maxLength="200" value={password} onChange={(event) => setPassword(event.target.value)} />
          </label>
          {error && <p className="form-alert" role="alert">{error}</p>}
          <button className="button button-primary button-wide" type="submit" disabled={busy}>{busy ? "Signing in…" : "Sign in"}</button>
        </form>
        <Link className="login-back" to="/">← Back to portfolio</Link>
      </section>
    </main>
  );
}

import { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import api from "../api/client";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem("token"));

  useEffect(() => {
    const expire = () => setToken(null);
    window.addEventListener("portfolio:auth-expired", expire);
    return () => window.removeEventListener("portfolio:auth-expired", expire);
  }, []);

  const signIn = useCallback(async (email, password) => {
    const response = await api.post("/auth/login", { email, password });
    localStorage.setItem("token", response.data.token);
    setToken(response.data.token);
  }, []);

  const signOut = useCallback(() => {
    localStorage.removeItem("token");
    setToken(null);
  }, []);

  const value = useMemo(() => ({
    token,
    isAuthenticated: Boolean(token),
    signIn,
    signOut,
  }), [token, signIn, signOut]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const value = useContext(AuthContext);
  if (!value) throw new Error("useAuth must be used inside AuthProvider.");
  return value;
}

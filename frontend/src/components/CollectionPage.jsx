import { useCallback, useEffect, useMemo, useState } from "react";
import api from "../api/client";

function emptyForm(fields) {
  return Object.fromEntries(fields.map((field) => [field.name, field.type === "checkbox" ? false : ""]));
}

function displayValue(value) {
  if (value == null || value === "") return "";
  return String(value);
}

export default function CollectionPage({ definition }) {
  const initial = useMemo(() => emptyForm(definition.fields), [definition]);
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(initial);
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");

  const loadItems = useCallback(async () => {
    setError("");
    try {
      const response = await api.get(definition.endpoint);
      setItems(Array.isArray(response.data) ? response.data : []);
    } catch {
      setError("Content could not be loaded. Try again or sign in again.");
    } finally {
      setLoading(false);
    }
  }, [definition.endpoint]);

  useEffect(() => {
    loadItems();
  }, [loadItems]);

  function change(name, value) {
    setForm((current) => {
      const next = { ...current, [name]: value };
      if (name === "currentlyWorking" && value) next.endDate = "";
      return next;
    });
  }

  function edit(item) {
    const next = emptyForm(definition.fields);
    definition.fields.forEach((field) => {
      if (item[field.name] != null) next[field.name] = item[field.name];
    });
    setEditingId(item.id);
    setForm(next);
    setNotice("");
    setError("");
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  function cancelEdit() {
    setEditingId(null);
    setForm(initial);
    setError("");
    setNotice("");
  }

  function toPayload() {
    const payload = {};
    definition.fields.forEach((field) => {
      const value = form[field.name];
      if (field.type === "checkbox") payload[field.name] = Boolean(value);
      else if (field.type === "number") payload[field.name] = value === "" ? null : Number(value);
      else payload[field.name] = value === "" ? null : value;
    });
    return payload;
  }

  async function submit(event) {
    event.preventDefault();
    setBusy(true);
    setError("");
    setNotice("");
    try {
      const payload = toPayload();
      if (editingId) await api.put(definition.endpoint + "/" + editingId, payload);
      else await api.post(definition.endpoint, payload);
      setNotice(editingId ? "Changes saved." : "Content added.");
      setEditingId(null);
      setForm(initial);
      await loadItems();
    } catch (requestError) {
      const serverError = requestError.response && requestError.response.data && requestError.response.data.error;
      setError(serverError || "This item could not be saved.");
    } finally {
      setBusy(false);
    }
  }

  async function remove(item) {
    const label = item[definition.titleField] || "this item";
    if (!window.confirm("Delete " + label + " from the portfolio?")) return;
    setError("");
    setNotice("");
    try {
      await api.delete(definition.endpoint + "/" + item.id);
      if (editingId === item.id) cancelEdit();
      setNotice("Item deleted.");
      await loadItems();
    } catch {
      setError("This item could not be deleted.");
    }
  }

  return (
    <div className="admin-page">
      <div className="page-heading">
        <div><p className="eyebrow">PORTFOLIO CONTENT</p><h1>{definition.title}</h1><p>{definition.intro}</p></div>
        <span className="item-count">{items.length} {items.length === 1 ? "item" : "items"}</span>
      </div>

      <section className="panel editor-panel">
        <div className="editor-title"><div><p className="eyebrow">{editingId ? "UPDATE ENTRY" : "NEW ENTRY"}</p><h2>{editingId ? "Edit " + definition.title.toLowerCase().replace(/s$/, "") : "Add " + definition.title.toLowerCase().replace(/s$/, "")}</h2></div></div>
        <form onSubmit={submit}>
          <div className="field-grid">
            {definition.fields.map((field) => {
              if (field.type === "checkbox") {
                return (
                  <label className="check-field field-full" key={field.name}>
                    <input type="checkbox" checked={Boolean(form[field.name])} onChange={(event) => change(field.name, event.target.checked)} />
                    <span>{field.label}</span>
                  </label>
                );
              }
              return (
                <label className={"field" + (field.full ? " field-full" : "")} key={field.name}>
                  <span>{field.label}{field.required ? " *" : ""}</span>
                  {field.type === "textarea"
                    ? <textarea rows="4" value={form[field.name] || ""} required={Boolean(field.required)} maxLength={field.maxLength || 3000} onChange={(event) => change(field.name, event.target.value)} />
                    : <input type={field.type} value={displayValue(form[field.name])} required={Boolean(field.required)} min={field.min} max={field.max} maxLength={field.type === "number" || field.type === "date" ? undefined : (field.maxLength || 255)} disabled={Boolean(field.disabledWhenCurrent && form.currentlyWorking)} onChange={(event) => change(field.name, event.target.value)} />}
                  {field.hint && <small>{field.hint}</small>}
                </label>
              );
            })}
          </div>
          {error && <p className="form-alert" role="alert">{error}</p>}
          {notice && <p className="form-success" role="status">{notice}</p>}
          <div className="form-actions">
            <button className="button button-primary" type="submit" disabled={busy}>{busy ? "Saving…" : editingId ? "Save changes" : "Add item"}</button>
            {editingId && <button className="button button-subtle" type="button" onClick={cancelEdit}>Cancel</button>}
          </div>
        </form>
      </section>

      <section className="collection-list">
        <div className="section-heading compact"><p className="eyebrow">SAVED CONTENT</p><h2>Current {definition.title.toLowerCase()}</h2></div>
        {loading ? <p className="empty-state">Loading…</p> : items.length === 0 ? <p className="empty-state">Nothing here yet. Add your first item above.</p> : (
          <div className="collection-grid">
            {items.map((item) => (
              <article className="collection-card panel" key={item.id}>
                <div className="collection-card-heading"><div><h3>{item[definition.titleField]}</h3>{definition.subtitleField && <p>{item[definition.subtitleField]}</p>}</div>{item.featured && <span className="featured-badge">FEATURED</span>}</div>
                {item.startDate && <p className="muted-copy">{item.startDate}{item.currentlyWorking ? " — Present" : item.endDate ? " — " + item.endDate : ""}</p>}
                {item.issueDate && <p className="muted-copy">{item.issueDate}</p>}
                {item.startYear && <p className="muted-copy">{item.startYear}{item.endYear ? " — " + item.endYear : ""}</p>}
                {definition.descriptionField && item[definition.descriptionField] && <p className="record-description">{item[definition.descriptionField]}</p>}
                <div className="collection-actions">
                  <button className="button button-subtle button-small" type="button" onClick={() => edit(item)}>Edit</button>
                  <button className="button button-danger-outline button-small" type="button" onClick={() => remove(item)}>Delete</button>
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}

const API_BASE_URL = "http://localhost:8080";

async function apiRequest(path, options = {}) {
  const token = localStorage.getItem("jwt");

  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(API_BASE_URL + path, {
    ...options,
    headers,
  });

  if (!response.ok) {
    // Intentamos leer el cuerpo por si viene un mensaje
    let errorDetail = "";
    try {
      const data = await response.json();
      errorDetail = data.message || JSON.stringify(data);
    } catch {
      errorDetail = await response.text();
    }
    throw new Error(
      `Error ${response.status}: ${response.statusText} ${
        errorDetail ? "- " + errorDetail : ""
      }`
    );
  }

  if (response.status === 204) return null; // No Content
  return response.json();
}
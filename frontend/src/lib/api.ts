import axios from "axios";

const isDev = import.meta.env.DEV;

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

if (isDev) {
  console.info("[API] Base URL:", import.meta.env.VITE_API_URL);
}

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  if (isDev) {
    console.debug("[API REQUEST]", {
      method: config.method,
      url: config.url,
      headers: config.headers,
    });
  }

  return config;
});

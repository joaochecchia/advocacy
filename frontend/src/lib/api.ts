import axios from "axios";
import { getApiBaseURL } from "./config";

const isDev = import.meta.env.DEV;

// Usa função centralizada para obter URL da API
const apiBaseURL = getApiBaseURL();

export const api = axios.create({
  baseURL: apiBaseURL,
});

if (isDev) {
  console.info("[API] Base URL:", apiBaseURL);
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

// Interceptor de resposta para tratamento de erros
api.interceptors.response.use(
  (response) => {
    // Resposta bem-sucedida
    if (isDev) {
      console.debug("[API RESPONSE]", {
        status: response.status,
        url: response.config.url,
        data: response.data,
      });
    }
    return response;
  },
  (error) => {
    // Tratamento de erros
    if (isDev) {
      console.error("[API ERROR]", {
        status: error.response?.status,
        url: error.config?.url,
        message: error.message,
        data: error.response?.data,
      });
    }

    // Se for erro 401 (não autorizado), limpar token e redirecionar
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("userType");
      localStorage.removeItem("email");
      // Não redirecionar automaticamente aqui, deixar o componente tratar
    }

    // Propaga o erro para ser tratado no componente
    return Promise.reject(error);
  }
);

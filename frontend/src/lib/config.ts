/**
 * Configurações centralizadas da aplicação
 * Centraliza o uso de variáveis de ambiente e URLs
 */

/**
 * Obtém a URL base da API
 * Usa variável de ambiente VITE_API_URL ou fallback apropriado
 */
export const getApiBaseURL = (): string => {
  const apiUrl = import.meta.env.VITE_API_URL;
  
  if (apiUrl) {
    return apiUrl.replace(/\/$/, "");
  }
  
  if (import.meta.env.DEV) {
    console.warn("[Config] VITE_API_URL não definida, usando localhost:8080/api");
    return "http://localhost:8080/api";
  }
  
  if (typeof window !== "undefined") {
    const origin = window.location.origin;
    const baseUrl = origin.replace(/:\d+$/, "") + ":8080";
    console.warn(
      "[Config] VITE_API_URL não definida em produção, usando:",
      baseUrl
    );
    return baseUrl;
  }
  
  return "http://localhost:8080/api";
};

/**
 * URL base da API (exportada como constante para compatibilidade)
 * Nota: Esta constante é calculada em tempo de importação
 */
export const API_BASE_URL = getApiBaseURL();

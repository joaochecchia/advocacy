/**
 * Configurações centralizadas da aplicação
 * Centraliza o uso de variáveis de ambiente e URLs
 */

/**
 * Obtém a URL base da API
 * Usa variável de ambiente VITE_API_URL ou fallback apropriado
 */
export const getApiBaseURL = (): string => {
  // Primeiro, verifica se VITE_API_URL está definida
  const apiUrl = import.meta.env.VITE_API_URL;
  
  if (apiUrl) {
    // Remove barra final se existir para evitar URLs duplicadas
    return apiUrl.replace(/\/$/, "");
  }
  
  // Fallback para desenvolvimento local
  if (import.meta.env.DEV) {
    console.warn("[Config] VITE_API_URL não definida, usando localhost:8080");
    return "http://localhost:8080";
  }
  
  // Em produção sem VITE_API_URL configurada
  // Tenta inferir da URL atual (assumindo mesmo domínio, porta diferente)
  if (typeof window !== "undefined") {
    const origin = window.location.origin;
    // Remove porta se existir e adiciona porta 8080
    const baseUrl = origin.replace(/:\d+$/, "") + ":8080";
    console.warn(
      "[Config] VITE_API_URL não definida em produção, usando:",
      baseUrl
    );
    return baseUrl;
  }
  
  // Fallback final (não deve acontecer em runtime normal)
  return "http://localhost:8080";
};

/**
 * URL base da API (exportada como constante para compatibilidade)
 * Nota: Esta constante é calculada em tempo de importação
 */
export const API_BASE_URL = getApiBaseURL();

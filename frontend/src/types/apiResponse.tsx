/* ==========================
   Resposta genérica da API
========================== */

export type ApiResponse<T> = {
  Message: string
  Body: T
}
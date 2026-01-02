export type OrigemMensagem = "CLIENTE" | "ADVOGADO" | "SISTEMA";

export interface Mensagem {
  id: number;
  chatId: number;
  remetenteId: number;
  conteudo: string;
  origem: OrigemMensagem;
  criadoEm: string; 
}

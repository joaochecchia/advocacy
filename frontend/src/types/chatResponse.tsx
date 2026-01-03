import { Chat } from "./Chat";

export interface ChatResponse {
  chat: Chat;
  clienteId: number;
  clienteNome: string;
  ativo: boolean
  ultimaMensagem: string | null;
  dataUltimaMensagem: string | null;
}

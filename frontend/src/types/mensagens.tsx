export type TipoMensagem = "USUARIO" | "SISTEMA" | "GPT";

export interface Mensagem {
  id: number;
  chatId: number;
  UsuarioId: number;
  tipo: TipoMensagem;
  conteudo: string;
  dataCriacao: string;
}
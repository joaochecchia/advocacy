/* =======================
   Tipo do advogado
======================= */

export interface Advogado {
  idUsuario: number
  nome: string
  email: string
  tipoUsuario: string
  ativo: boolean
  criadoEmUsuario: string | null
  atualizadoEmUsuario: string | null
  idAdvogado: number
  oab: string
  especialidade: string
  criadoEmAdvogado: string | null
}
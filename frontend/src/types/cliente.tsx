/* =======================
   Tipo do cliente
======================= */

export interface Cliente {
  idUsuario: number
  nome: string
  email: string
  // No backend vem como `role` (ex: "CLIENTE"/"ADVOGADO")
  tipoUsuario: string
  ativo: boolean
  criadoEmUsuario: string | null
  atualizadoEmUsuario: string | null
  idCliente: number
  cpf: string
  telefone: string
  criadoEmCliente: string | null
  chatIds: number[]
}
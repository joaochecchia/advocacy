import { api } from "@/lib/api";
import { Cliente } from "@/types/cliente";
import { ApiResponse } from "@/types/apiResponse";

export const getCliente = async (): Promise<Cliente | null> => {
  const userId = localStorage.getItem("userId");

  if (!userId) {
    throw new Error("UserId não encontrado no localStorage");
  }

  // Backend retorna `ClienteResponse` (campos `usuarioId` / `clienteId`).
  // Aqui normalizamos para o shape usado no frontend (`idUsuario` / `idCliente`).
  const response = await api.get<ApiResponse<any>>(
    `/cliente/getClienteByUserId/${userId}`
  );

  const body = response.data.Body ?? {};

  const cliente: Cliente = {
    ...body,
    idUsuario: body.idUsuario ?? body.usuarioId,
    idCliente: body.idCliente ?? body.clienteId,
    tipoUsuario: body.tipoUsuario ?? body.role,
  };

  localStorage.setItem("Cliente", JSON.stringify(cliente));

  return cliente;
};

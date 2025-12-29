import { api } from "@/lib/api";
import { Cliente } from "@/types/cliente";
import { ApiResponse } from "@/types/apiResponse";

export const getCliente = async (): Promise<Cliente | null> => {
  const storedCliente = localStorage.getItem("Cliente");

  if (storedCliente) {
    return JSON.parse(storedCliente) as Cliente;
  }

  const userId = localStorage.getItem("userId");

  if (!userId) {
    throw new Error("UserId não encontrado no localStorage");
  }

  const response = await api.get<ApiResponse<Cliente>>(
    `/cliente/getClienteByUserId/${userId}`
  );

  const cliente = response.data.Body;

  localStorage.setItem("Cliente", JSON.stringify(cliente));

  return cliente;
};

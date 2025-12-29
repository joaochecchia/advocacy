import { api } from "@/lib/api";
import { Advogado } from "@/types/advogado";
import { ApiResponse } from "@/types/apiResponse";

export const getAdvogado = async (): Promise<Advogado | null> => {
  const storedAdvogado = localStorage.getItem("Advogado");

  if (storedAdvogado) {
    return JSON.parse(storedAdvogado) as Advogado;
  }

  const userId = localStorage.getItem("userId");

  if (!userId) {
    throw new Error("UserId não encontrado no localStorage");
  }

  const response = await api.get<ApiResponse<Advogado>>(
    `/advogado/getAdvogadoByUserId/${userId}`
  );

  const advogado = response.data.Body;

  localStorage.setItem("Advogado", JSON.stringify(advogado));

  return advogado;
};

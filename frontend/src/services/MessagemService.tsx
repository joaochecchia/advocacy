import { api } from "@/lib/api";
import { ApiResponse } from "@/types/apiResponse";
import { Mensagem } from "@/types/mensagens";

export const getMensagem = async (
  chatId: number,
  page: number
): Promise<Mensagem[]> => {
  const response = await api.get<ApiResponse<Mensagem[]>>(
    `/mensagem/chat/${chatId}/mensagens?page=${page}`
  );

  return response.data.Body;
};

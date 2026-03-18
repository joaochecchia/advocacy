
import { api } from "@/lib/api";
import { Chat } from "@/types/Chat";
import { ApiResponse } from "@/types/apiResponse";
import { ChatResponse } from "@/types/chatResponse";
import { GetAllChatsResponse } from "@/types/getAllChatsResponse";
import { getCliente } from "@/services/ClienteService";

export const getAllChatsByClienteId = async (): Promise<Chat[] | null> => {
  const cliente = await getCliente();
  const clienteId = cliente?.idCliente;

  if (!clienteId) {
    throw new Error("Cliente inválido: idCliente não encontrado");
  }

  try {
    const response = await api.get<ApiResponse<Chat[]>>(
      `/chat/getAllChatsByUserId/${clienteId}`
    );

    const chats = response.data.Body;

    // Sempre atualiza o localStorage com os dados mais recentes do servidor
    localStorage.setItem("Chats", JSON.stringify(chats));

    return chats;
  } catch (error: any) {
    // Se houver erro, tenta usar o cache do localStorage como fallback
    const storedChat = localStorage.getItem("Chats");
    if (storedChat) {
      console.warn("Erro ao buscar chats do servidor, usando cache local");
      return JSON.parse(storedChat) as Chat[];
    }
    throw error;
  }
};

export const getAllChats = async (): Promise<ChatResponse[]> => {
  try {
    const response = await api.get<GetAllChatsResponse>("/chat/getAll");

    return response.data.body;
  } catch (error: any) {
    console.error("Erro ao buscar chats:", error);

    return [];
  }
};
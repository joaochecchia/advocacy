
import { api } from "@/lib/api";
import { Chat } from "@/types/Chat";
import { ApiResponse } from "@/types/apiResponse";
import { ChatResponse } from "@/types/chatResponse";
import { GetAllChatsResponse } from "@/types/getAllChatsResponse";
import { Cliente } from "@/types/cliente";

export const getAllChatsByClienteId = async (): Promise<Chat[] | null> => {
  const storedChat = localStorage.getItem("Chats");

  if (storedChat) {
    return JSON.parse(storedChat) as Chat[];
  }

  const clienteStr = localStorage.getItem("Cliente");

  if (!clienteStr) {
    throw new Error("Cliente não encontrado no localStorage");
  }

  const cliente = JSON.parse(clienteStr) as Cliente;
  const clienteId = cliente.idCliente;

  const response = await api.get<ApiResponse<Chat[]>>(
    `/chat/getAllChatsByUserId/${clienteId}`
  );

  const chats = response.data.Body;

  localStorage.setItem("Chats", JSON.stringify(chats));

  return chats;
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
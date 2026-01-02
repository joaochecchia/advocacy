import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";

import { Cliente } from "@/types/cliente";
import { Chat } from "@/types/Chat";
import { Mensagem } from "@/types/mensagens";

import { getCliente } from "@/services/ClienteService";
import { getAllChatsByClienteId } from "@/services/ChatService";
import { getMensagem } from "@/services/MessagemService";

/* =======================
   Tipagem do Context
======================= */

interface ChatContextType {
  cliente: Cliente | null;
  chats: Chat[] | null;
  chatAtivo: Chat | null;
  mensagens: Mensagem[];
  loading: boolean;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  refreshChats: () => Promise<void>;
  refreshCliente: () => Promise<void>;
  carregarMensagens: (chatId: number, page?: number) => Promise<void>;
}

/* =======================
   Context
======================= */

const ChatContext = createContext<ChatContextType | undefined>(undefined);

/* =======================
   Provider
======================= */

export const ChatProvider = ({ children }: { children: ReactNode }) => {
  const [cliente, setCliente] = useState<Cliente | null>(null);
  const [chats, setChats] = useState<Chat[] | null>(null);
  const [chatAtivo, setChatAtivo] = useState<Chat | null>(null);
  const [mensagens, setMensagens] = useState<Mensagem[]>([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);

  /* =======================
     Load inicial
  ======================= */

  useEffect(() => {
    const carregarInicial = async () => {
      try {
        const clienteData = await getCliente();
        setCliente(clienteData);

        const chatsData = await getAllChatsByClienteId();
        setChats(chatsData);

        if (chatsData.length === 0) return;

        const chatInicial = chatsData[0];
        setChatAtivo(chatInicial);

        const mensagensData = await getMensagem(chatInicial.id, 0);
        setMensagens(mensagensData);

      } catch (error) {
        console.error("Erro no load inicial do chat", error);
      } finally {
        setLoading(false);
      }
    };

    carregarInicial();
  }, []);

  /* =======================
     Mensagens (paginação)
  ======================= */

  const carregarMensagens = async (chatId: number, novaPage = page) => {
    const novasMensagens = await getMensagem(chatId, novaPage);

    if (novaPage === 0) {
      setMensagens(novasMensagens);
    } else {
      setMensagens((prev) => [...prev, ...novasMensagens]);
    }
  };

  /* =======================
     Refresh Chats
  ======================= */

  const refreshChats = async () => {
    const chatsData = await getAllChatsByClienteId();
    setChats(chatsData);
  };

  /* =======================
     Refresh Cliente
  ======================= */

  const refreshCliente = async () => {
    const clienteData = await getCliente();
    setCliente(clienteData);
  };

  return (
    <ChatContext.Provider
      value={{
        cliente,
        chats,
        chatAtivo,
        mensagens,
        loading,
        page,
        setPage,
        refreshChats,
        refreshCliente,
        carregarMensagens,
      }}
    >
      {children}
    </ChatContext.Provider>
  );
};

/* =======================
   Hook
======================= */

export const useChat = () => {
  const context = useContext(ChatContext);

  if (!context) {
    throw new Error("useChat deve ser usado dentro de ChatProvider");
  }

  return context;
};

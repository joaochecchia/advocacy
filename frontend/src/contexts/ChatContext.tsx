import React, {
  createContext,
  useContext,
  useEffect,
  useRef,
  useState,
  ReactNode,
} from "react";

import { Cliente } from "@/types/cliente";
import { Chat } from "@/types/Chat";
import { Mensagem } from "@/types/mensagens";

import { getCliente } from "@/services/ClienteService";
import { getAllChatsByClienteId } from "@/services/ChatService";
import { getMensagem } from "@/services/MessagemService";

import SockJS from "sockjs-client";
import Stomp, { Client } from "stompjs";

/* =======================
   Tipagem
======================= */

interface ChatContextType {
  cliente: Cliente | null;
  chats: Chat[] | null;
  chatAtivo: Chat | null;
  mensagens: Mensagem[];
  loading: boolean;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  carregarMensagens: (chatId: number, page?: number) => Promise<void>;
  enviarMensagem: (conteudo: string, usarGpt?: boolean) => void;
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

  const stompClientRef = useRef<Client | null>(null);

  const API_BASE = "http://localhost:8080";

  /* =======================
     Load inicial
  ======================= */

  useEffect(() => {
    const init = async () => {
      try {
        const clienteData = await getCliente();
        setCliente(clienteData);

        const chatsData = await getAllChatsByClienteId();
        setChats(chatsData);

        if (!chatsData.length) return;

        setChatAtivo(chatsData[0]);

        const msgs = await getMensagem(chatsData[0].id, 0);
        setMensagens(msgs);

      } catch (e) {
        console.error("Erro ao iniciar chat", e);
      } finally {
        setLoading(false);
      }
    };

    init();
  }, []);

  /* =======================
     Conectar WebSocket
  ======================= */

  useEffect(() => {
    if (!chatAtivo) return;

    const token = localStorage.getItem("token");
    if (!token) return;

    const socket = new SockJS(
      `${API_BASE}/build-chat?token=${token}`
    );

    const stomp = Stomp.over(socket);
    stomp.debug = () => {}; // silencia logs

    stomp.connect({}, () => {
      stomp.subscribe(
        `/topics/chat/${chatAtivo.id}`,
        (message) => {
          const msg: Mensagem = JSON.parse(message.body);

          setMensagens((prev) => [...prev, msg]);
        }
      );
    });

    stompClientRef.current = stomp;

    return () => {
      stomp.disconnect(() => {});
      stompClientRef.current = null;
    };
  }, [chatAtivo]);

  /* =======================
     Paginação
  ======================= */

  const carregarMensagens = async (chatId: number, novaPage = page) => {
    const novas = await getMensagem(chatId, novaPage);

    if (novaPage === 0) {
      setMensagens(novas);
    } else {
      setMensagens((prev) => [...novas, ...prev]);
    }
  };

  /* =======================
     Enviar mensagem
  ======================= */

  const enviarMensagem = (conteudo: string, usarGpt = false) => {
    if (!stompClientRef.current || !chatAtivo) return;

    stompClientRef.current.send(
      `/advocacy-chat/new-message/${chatAtivo.id}`,
      {},
      JSON.stringify({
        message: conteudo,
        chatId: chatAtivo.id,
        gpt: usarGpt,
      })
    );
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
        carregarMensagens,
        enviarMensagem,
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
  const ctx = useContext(ChatContext);
  if (!ctx) {
    throw new Error("useChat deve ser usado dentro de ChatProvider");
  }
  return ctx;
};

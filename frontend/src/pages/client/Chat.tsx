import { Layout } from "@/components/Layout";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";

import { Send, Bot, User } from "lucide-react";
import { toast } from "sonner";

import { useEffect, useRef, useState } from "react";
import { useChat } from "@/contexts/ChatContext";

import SockJS from "sockjs-client";
import Stomp from "stompjs";

const API_BASE = "http://localhost:8080";

export default function Chat() {
  /* =======================
     Context
  ======================= */

  const {
    mensagens,
    chatAtivo,
    loading,
    page,
    setPage,
    carregarMensagens,
  } = useChat();

  /* =======================
     State local
  ======================= */

  const [newMessage, setNewMessage] = useState("");
  const stompClientRef = useRef<Stomp.Client | null>(null);

  /* =======================
     Refs de scroll
  ======================= */

  const containerRef = useRef<HTMLDivElement>(null);
  const bottomRef = useRef<HTMLDivElement>(null);
  const firstLoadRef = useRef(true);

  /* =======================
     Load inicial
  ======================= */

  useEffect(() => {
    if (!chatAtivo) return;

    setPage(0);
    carregarMensagens(chatAtivo.id, 0);
    firstLoadRef.current = true;

    conectarWebSocket(chatAtivo.id);

    return () => {
      stompClientRef.current?.disconnect();
    };
  }, [chatAtivo]);

  /* =======================
     Scroll automático (somente 1ª vez)
  ======================= */

  useEffect(() => {
    if (firstLoadRef.current) {
      bottomRef.current?.scrollIntoView({ behavior: "auto" });
      firstLoadRef.current = false;
    }
  }, [mensagens]);

  /* =======================
     Scroll infinito (para cima)
  ======================= */

  const handleScroll = () => {
    const container = containerRef.current;
    if (!container || !chatAtivo) return;

    if (container.scrollTop === 0) {
      const nextPage = page + 1;
      setPage(nextPage);
      carregarMensagens(chatAtivo.id, nextPage);
    }
  };

  /* =======================
     WebSocket
  ======================= */

  const conectarWebSocket = (chatId: number) => {
    const token = localStorage.getItem("token");
    if (!token) {
      toast.error("Token não encontrado");
      return;
    }

    const socket = new SockJS(
      `${API_BASE}/build-chat?token=${token}`
    );

    const stompClient = Stomp.over(socket);
    stompClient.debug = () => {}; 

    stompClient.connect(
      {},
      () => {
        stompClient.subscribe(
          `/topics/chat/${chatId}`,
          (message) => {
            const body = JSON.parse(message.body);

            carregarMensagens(chatId, 0);
            bottomRef.current?.scrollIntoView({ behavior: "smooth" });
          }
        );
      },
      () => {
        toast.error("Erro ao conectar no chat");
      }
    );

    stompClientRef.current = stompClient;
  };

  /* =======================
     Enviar mensagem
  ======================= */

  const handleSend = () => {
    if (!newMessage.trim() || !chatAtivo) return;

    if (!stompClientRef.current) {
      toast.error("WebSocket não conectado");
      return;
    }

    stompClientRef.current.send(
      `/advocacy-chat/new-message/${chatAtivo.id}`,
      {},
      JSON.stringify({
        message: newMessage,
        chatId: chatAtivo.id,
        gpt: true, 
      })
    );

    setNewMessage("");
  };

  /* =======================
     Loading
  ======================= */

  if (loading) {
    return (
      <Layout>
        <div className="text-center mt-10">
          Carregando mensagens...
        </div>
      </Layout>
    );
  }

  if (!chatAtivo) {
    return (
      <Layout>
        <div className="text-center mt-10">
          Nenhum chat disponível.
        </div>
      </Layout>
    );
  }

  /* =======================
     Render
  ======================= */

  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">
          Chat Jurídico
        </h1>

        <Card className="h-[600px] flex flex-col">
          <CardHeader className="border-b">
            <CardTitle className="text-lg">
              Consultoria Jurídica
            </CardTitle>
          </CardHeader>

          {/* Mensagens */}
          <CardContent
            ref={containerRef}
            onScroll={handleScroll}
            className="flex-1 overflow-y-auto p-4 space-y-4"
          >
            {mensagens.map((mensagem, index) => {
              const isCliente =
                mensagem.origem === "CLIENTE";

              return (
                <div
                  key={`${mensagem.id}-${index}`}
                  className={`flex gap-3 ${
                    isCliente ? "flex-row-reverse" : ""
                  }`}
                >
                  {/* Avatar */}
                  <div
                    className={`w-10 h-10 rounded-full flex items-center justify-center ${
                      isCliente
                        ? "bg-primary text-primary-foreground"
                        : "bg-accent text-accent-foreground"
                    }`}
                  >
                    {isCliente ? (
                      <User size={20} />
                    ) : (
                      <Bot size={20} />
                    )}
                  </div>

                  {/* Bubble */}
                  <div
                    className={`max-w-[70%] p-4 rounded-lg ${
                      isCliente
                        ? "bg-primary text-primary-foreground"
                        : "bg-secondary"
                    }`}
                  >
                    <p>{mensagem.conteudo}</p>

                    {mensagem.criadoEm && (
                      <p className="text-xs mt-2 opacity-70">
                        {new Date(
                          mensagem.criadoEm
                        ).toLocaleTimeString("pt-BR", {
                          hour: "2-digit",
                          minute: "2-digit",
                        })}
                      </p>
                    )}
                  </div>
                </div>
              );
            })}

            <div ref={bottomRef} />
          </CardContent>

          {/* Input */}
          <div className="border-t p-4">
            <div className="flex gap-2">
              <Textarea
                value={newMessage}
                onChange={(e) =>
                  setNewMessage(e.target.value)
                }
                placeholder="Digite sua mensagem..."
                rows={3}
                onKeyDown={(e) => {
                  if (
                    e.key === "Enter" &&
                    !e.shiftKey
                  ) {
                    e.preventDefault();
                    handleSend();
                  }
                }}
              />

              <Button onClick={handleSend} size="icon">
                <Send size={20} />
              </Button>
            </div>
          </div>
        </Card>
      </div>
    </Layout>
  );
}

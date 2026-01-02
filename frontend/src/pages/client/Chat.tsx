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

  /* =======================
     Refs
  ======================= */

  const containerRef = useRef<HTMLDivElement>(null);
  const bottomRef = useRef<HTMLDivElement>(null);
  const firstLoadRef = useRef(true);

  /* =======================
     Load inicial do chat
  ======================= */

  useEffect(() => {
    if (!chatAtivo) return;

    setPage(0);
    carregarMensagens(chatAtivo.id, 0);
    firstLoadRef.current = true;
  }, [chatAtivo]);

  /* =======================
     Scroll automático para o final
     (somente no primeiro load)
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
     Enviar mensagem (mock)
  ======================= */

  const handleSend = () => {
    if (!newMessage.trim()) return;

    toast.success("Mensagem enviada");
    setNewMessage("");

    // 🔜 Aqui depois entra POST / WebSocket
  };

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

          {/* =======================
              Mensagens
          ======================= */}

          <CardContent
            ref={containerRef}
            onScroll={handleScroll}
            className="flex-1 overflow-y-auto p-4 space-y-4"
          >
            {mensagens.map((mensagem) => {
              const isCliente = mensagem.origem === "CLIENTE";

              return (
                <div
                  key={mensagem.id}
                  className={`flex gap-3 ${
                    isCliente ? "flex-row-reverse" : ""
                  }`}
                >
                  {/* Avatar */}
                  <div
                    className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center ${
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
                        {new Date(mensagem.criadoEm).toLocaleTimeString(
                          "pt-BR",
                          {
                            hour: "2-digit",
                            minute: "2-digit",
                          }
                        )}
                      </p>
                    )}
                  </div>
                </div>
              );
            })}

            {/* Âncora do fim do chat */}
            <div ref={bottomRef} />
          </CardContent>

          {/* =======================
              Input
          ======================= */}

          <div className="border-t p-4">
            <div className="flex gap-2">
              <Textarea
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                placeholder="Digite sua mensagem..."
                className="resize-none"
                rows={3}
                onKeyDown={(e) => {
                  if (e.key === "Enter" && !e.shiftKey) {
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

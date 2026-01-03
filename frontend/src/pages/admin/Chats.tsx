import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { Layout } from "@/components/Layout";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { MessageSquare, Clock } from "lucide-react";

import { getAllChats } from "@/services/ChatService";
import { ChatResponse } from "@/types/chatResponse";

export default function Chats() {
  const [chats, setChats] = useState<ChatResponse[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchChats = async () => {
      try {
        const data = await getAllChats();
        setChats(data);
      } catch (err) {
        console.error("Erro ao buscar chats:", err);
      }
    };

    fetchChats();
  }, []);

  return (
    <Layout>
      <div className="space-y-6">
        {/* Cabeçalho */}
        <div>
          <h1 className="text-3xl font-bold mb-2">Gerenciar Chats</h1>
          <p className="text-muted-foreground">
            Visualize e responda às conversas dos clientes
          </p>
        </div>

        {/* Lista de chats */}
        <div className="grid gap-4">
          {chats.map((chat) => (
            <Card key={chat.chat.id} className="hover:shadow-md transition-shadow">
              <CardContent className="pt-6">
                <div className="flex items-start justify-between">
                  <div className="flex gap-4 flex-1">
                    {/* Ícone */}
                    <div className="p-3 bg-primary/10 rounded-full flex-shrink-0">
                      <MessageSquare className="text-primary" size={24} />
                    </div>

                    {/* Conteúdo do chat */}
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center gap-2 mb-1">
                        {/* Nome do cliente */}
                        <h3 className="font-semibold text-lg">{chat.clienteNome}</h3>

                        {/* Status do chat */}
                        <Badge
                          variant={chat.ativo ? "default" : "secondary"}
                          className="ml-auto"
                        >
                          {chat.ativo ? "Ativo" : "Inativo"}
                        </Badge>
                      </div>

                      {/* Última mensagem */}
                      <p className="text-sm text-muted-foreground truncate mb-2">
                        {chat.ultimaMensagem || "Sem mensagens ainda"}
                      </p>

                      {/* Data da última mensagem */}
                      <div className="flex items-center gap-2 text-xs text-muted-foreground">
                        <Clock size={14} />
                        <span>
                          {chat.dataUltimaMensagem
                            ? new Date(chat.dataUltimaMensagem).toLocaleString()
                            : "-"}
                        </span>
                      </div>
                    </div>
                  </div>

                  {/* Botão abrir chat */}
                  <Button
                    className="ml-4"
                    onClick={() =>
                      navigate(`/admin/chat-view?chatId=${chat.chat.id}&clienteId=${chat.clienteId}`)
                    }
                  >
                    Abrir Chat
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </Layout>
  );
}

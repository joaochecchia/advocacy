import { useState, useEffect, useRef } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { Send, ArrowLeft, User } from 'lucide-react';
import { toast } from 'sonner';
import SockJS from 'sockjs-client';
import Stomp, { Client } from 'stompjs';

import { getMensagem } from '@/services/MessagemService';
import { Mensagem, OrigemMensagem } from '@/types/mensagens';

const API_BASE = 'http://localhost:8080';

interface Message {
  id: number;
  text: string;
  sender: 'client' | 'admin';
  timestamp: Date;
}

export default function ChatView() {
  /* =======================
     Hooks de navegação e parâmetros
     - useNavigate: permite navegar entre rotas
     - useSearchParams: lê parâmetros da URL (ex: chatId, clienteNome)
  ======================= */
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const chatIdParam = searchParams.get('chatId');
  const clienteNomeParam = searchParams.get('clienteNome');
  const chatAtivoParam = searchParams.get('ativo');

  /* =======================
     State local
     - messages: lista de mensagens do chat
     - newMessage: conteúdo do input para nova mensagem
     - loading: indica carregamento de mensagens
     - clienteNome: nome do cliente exibido no header
     - ativo: indica se o chat está ativo
  ======================= */
  const [messages, setMessages] = useState<Message[]>([]);
  const [newMessage, setNewMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [clienteNome, setClienteNome] = useState(clienteNomeParam || 'Cliente');
  const [ativo, setAtivo] = useState(chatAtivoParam === 'true');

  /* =======================
     Ref para WebSocket
     - Mantém a instância do cliente STOMP para enviar e receber mensagens
  ======================= */
  const stompClientRef = useRef<Client | null>(null);

  /* =======================
     Conversão de mensagens do backend para formato do frontend
     - Converte Mensagem (backend) para Message (frontend)
     - Define sender baseado na origem da mensagem
     - Garante timestamp válido
  ======================= */
  const converterMensagem = (msg: Mensagem): Message => {
    let sender: 'client' | 'admin' = 'client';
    if (msg.origem === 'ADVOGADO' || msg.origem === 'GPT') {
      sender = 'admin';
    } else if (msg.origem === 'CLIENTE') {
      sender = 'client';
    }

    let timestamp: Date;
    if (msg.criadoEm) {
      timestamp = new Date(msg.criadoEm);
      if (isNaN(timestamp.getTime())) {
        timestamp = new Date();
      }
    } else {
      timestamp = new Date();
    }

    return {
      id: msg.id,
      text: msg.conteudo,
      sender,
      timestamp,
    };
  };

  /* =======================
     Carregar mensagens do chat
     - Chama serviço getMensagem
     - Converte mensagens para frontend
     - Atualiza state e loading
  ======================= */
  const carregarMensagens = async () => {
    if (!chatIdParam) {
      setLoading(false);
      return;
    }

    try {
      const chatId = parseInt(chatIdParam);
      const mensagens = await getMensagem(chatId, 0);
      const mensagensConvertidas = mensagens.map(converterMensagem);
      setMessages(mensagensConvertidas);
    } catch (error) {
      console.error('Erro ao carregar mensagens:', error);
      toast.error('Erro ao carregar mensagens');
    } finally {
      setLoading(false);
    }
  };

  /* =======================
     useEffect para load inicial
     - Carrega mensagens quando chatId muda
  ======================= */
  useEffect(() => {
    carregarMensagens();
  }, [chatIdParam]);

  /* =======================
     Conexão WebSocket
     - Configura SockJS + STOMP
     - Recebe mensagens em tempo real via /topics/chat/{chatId}
     - Atualiza mensagens automaticamente
     - Desconecta ao desmontar componente
  ======================= */
  useEffect(() => {
    if (!chatIdParam) return;

    const token = localStorage.getItem('token');
    if (!token) {
      toast.error('Token não encontrado');
      return;
    }

    const chatId = parseInt(chatIdParam);

    const socket = new SockJS(`${API_BASE}/build-chat?token=${token}`);
    const stomp = Stomp.over(socket);
    stomp.debug = () => {}; // desativa logs do STOMP

    stomp.connect(
      {},
      () => {
        stomp.subscribe(`/topics/chat/${chatId}`, (message) => {
          try {
            carregarMensagens(); // Atualiza mensagens ao receber nova
          } catch (error) {
            console.error('Erro ao processar mensagem recebida:', error);
          }
        });
      },
      (error) => {
        console.error('Erro ao conectar no WebSocket:', error);
        toast.error('Erro ao conectar no chat');
      }
    );

    stompClientRef.current = stomp;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.disconnect(() => {});
        stompClientRef.current = null;
      }
    };
  }, [chatIdParam]);

  /* =======================
     Enviar nova mensagem
     - Verifica conteúdo e conexão WebSocket
     - Envia mensagem via STOMP
     - Limpa input e mostra toast
  ======================= */
  const handleSend = () => {
    if (!newMessage.trim() || !chatIdParam) return;

    if (!stompClientRef.current) {
      toast.error('WebSocket não conectado');
      return;
    }

    const chatId = parseInt(chatIdParam);

    try {
      stompClientRef.current.send(
        `/advocacy-chat/new-message/${chatId}`,
        {},
        JSON.stringify({
          message: newMessage,
          chatId: chatId,
          gpt: false,
        })
      );

      setNewMessage('');
      toast.success('Mensagem enviada');
    } catch (error) {
      console.error('Erro ao enviar mensagem:', error);
      toast.error('Erro ao enviar mensagem');
    }
  };

  /* =======================
     Loading ou chat inexistente
     - Exibe mensagens de carregamento ou chat não encontrado
  ======================= */
  if (!chatIdParam) {
    return (
      <Layout>
        <div className="text-center py-12">
          <h2 className="text-2xl font-bold mb-4">Chat não encontrado</h2>
          <Button onClick={() => navigate('/admin/chats')}>Voltar</Button>
        </div>
      </Layout>
    );
  }

  if (loading) {
    return (
      <Layout>
        <div className="text-center py-12">
          <h2 className="text-2xl font-bold mb-4">Carregando chat...</h2>
        </div>
      </Layout>
    );
  }

  /* =======================
     Render do chat
     - Header com nome do cliente e status
     - Lista de mensagens
     - Input para enviar mensagem
  ======================= */
  return (
    <Layout>
      <div className="max-w-4xl mx-auto space-y-4">
        {/* Botão de voltar */}
        <Button variant="outline" onClick={() => navigate('/admin/chats')} className="mb-4">
          <ArrowLeft className="mr-2" size={16} />
          Voltar
        </Button>

        <Card className="h-[600px] flex flex-col">
          {/* Header do chat */}
          <CardHeader className="border-b">
            <div className="flex items-center justify-between">
              <CardTitle className="text-lg flex items-center gap-2">
                <User size={20} />
                {clienteNome}
              </CardTitle>
              <Badge variant={ativo ? 'default' : 'secondary'}>
                {ativo ? 'Ativo' : 'Inativo'}
              </Badge>
            </div>
          </CardHeader>

          {/* Conteúdo das mensagens */}
          <CardContent className="flex-1 overflow-y-auto p-4 space-y-4">
            {messages.length === 0 ? (
              <div className="text-center text-muted-foreground py-8">
                Nenhuma mensagem ainda. Comece a conversa!
              </div>
            ) : (
              messages.map((message, index) => (
                <div
                  key={`${message.id}-${index}`}
                  className={`flex gap-3 ${message.sender === 'admin' ? 'flex-row-reverse' : ''}`}
                >
                  {/* Avatar da mensagem */}
                  <div
                    className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center ${
                      message.sender === 'admin'
                        ? 'bg-primary text-primary-foreground'
                        : 'bg-accent text-accent-foreground'
                    }`}
                  >
                    {message.sender === 'admin' ? 'A' : 'C'}
                  </div>

                  {/* Bubble da mensagem */}
                  <div
                    className={`max-w-[70%] p-4 rounded-lg ${
                      message.sender === 'admin'
                        ? 'bg-primary text-primary-foreground'
                        : 'bg-secondary'
                    }`}
                  >
                    <p>{message.text}</p>
                    <p className="text-xs mt-2 opacity-70">
                      {message.timestamp.toLocaleTimeString('pt-BR', {
                        hour: '2-digit',
                        minute: '2-digit',
                      })}
                    </p>
                  </div>
                </div>
              ))
            )}
          </CardContent>

          {/* Input de nova mensagem */}
          <div className="border-t p-4">
            <div className="flex gap-2">
              <Textarea
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                placeholder="Digite sua resposta..."
                className="resize-none"
                rows={3}
                onKeyDown={(e) => {
                  if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    handleSend();
                  }
                }}
              />
              <Button onClick={handleSend} size="icon" className="self-end">
                <Send size={20} />
              </Button>
            </div>
          </div>
        </Card>
      </div>
    </Layout>
  );
}

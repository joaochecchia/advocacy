import { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { Send, ArrowLeft, User } from 'lucide-react';
import { toast } from 'sonner';

interface Message {
  id: number;
  text: string;
  sender: 'client' | 'admin';
  timestamp: Date;
}

const mockChats = {
  '1': {
    client: 'Maria Silvaaaa',
    messages: [
      { id: 1, text: 'Obrigaasdasdadda pela ajuda com o contrato...', sender: 'client' as const, timestamp: new Date() },
      { id: 2, text: 'Olá Maria, estou à disposição para ajudar.', sender: 'admin' as const, timestamp: new Date() },
    ],
  },
  '2': {
    client: 'João Santos',
    messages: [
      { id: 1, text: 'Quando posso marcar uma reunião?', sender: 'client' as const, timestamp: new Date() },
    ],
  },
  '3': {
    client: 'Ana Costa',
    messages: [
      { id: 1, text: 'Perfeito, vou enviar os documentos.', sender: 'client' as const, timestamp: new Date() },
    ],
  },
  '4': {
    client: 'Pedro Oliveira',
    messages: [
      { id: 1, text: 'Preciso de orientação sobre...', sender: 'client' as const, timestamp: new Date() },
    ],
  },
};

export default function ChatView() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const chatId = searchParams.get('id') || '1';
  const chat = mockChats[chatId as keyof typeof mockChats];

  const [messages, setMessages] = useState<Message[]>(chat?.messages || []);
  const [newMessage, setNewMessage] = useState('');

  const handleSend = () => {
    if (!newMessage.trim()) return;

    const adminMessage: Message = {
      id: messages.length + 1,
      text: newMessage,
      sender: 'admin',
      timestamp: new Date(),
    };

    setMessages([...messages, adminMessage]);
    setNewMessage('');
    toast.success('Mensagem enviada');
  };

  if (!chat) {
    return (
      <Layout>
        <div className="text-center py-12">
          <h2 className="text-2xl font-bold mb-4">Chat não encontrado</h2>
          <Button onClick={() => navigate('/admin/chats')}>Voltar</Button>
        </div>
      </Layout>
    );
  }

  return (
    <Layout>
      <div className="max-w-4xl mx-auto space-y-4">
        <Button variant="outline" onClick={() => navigate('/admin/chats')} className="mb-4">
          <ArrowLeft className="mr-2" size={16} />
          Voltar
        </Button>

        <Card className="h-[600px] flex flex-col">
          <CardHeader className="border-b">
            <div className="flex items-center justify-between">
              <CardTitle className="text-lg flex items-center gap-2">
                <User size={20} />
                {chat.client}
              </CardTitle>
              <Badge>Ativo</Badge>
            </div>
          </CardHeader>

          <CardContent className="flex-1 overflow-y-auto p-4 space-y-4">
            {messages.map((message) => (
              <div
                key={message.id}
                className={`flex gap-3 ${
                  message.sender === 'admin' ? 'flex-row-reverse' : ''
                }`}
              >
                <div
                  className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center ${
                    message.sender === 'admin'
                      ? 'bg-primary text-primary-foreground'
                      : 'bg-accent text-accent-foreground'
                  }`}
                >
                  {message.sender === 'admin' ? 'A' : 'C'}
                </div>
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
            ))}
          </CardContent>

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

import { useState } from 'react';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Send, Bot, User } from 'lucide-react';
import { toast } from 'sonner';

interface Message {
  id: number;
  text: string;
  sender: 'user' | 'lawyer';
  timestamp: Date;
}

export default function Chat() {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 1,
      text: 'Olá! Bem-vindo ao chat jurídico. Como posso ajudá-lo hoje?',
      sender: 'lawyer',
      timestamp: new Date(),
    },
  ]);
  const [newMessage, setNewMessage] = useState('');

  const handleSend = () => {
    if (!newMessage.trim()) return;

    const userMessage: Message = {
      id: messages.length + 1,
      text: newMessage,
      sender: 'user',
      timestamp: new Date(),
    };

    setMessages([...messages, userMessage]);
    setNewMessage('');

    // Simula resposta do advogado
    setTimeout(() => {
      const lawyerMessage: Message = {
        id: messages.length + 2,
        text: 'Recebi sua mensagem. Vou analisar e responder em breve.',
        sender: 'lawyer',
        timestamp: new Date(),
      };
      setMessages(prev => [...prev, lawyerMessage]);
    }, 1500);

    toast.success('Mensagem enviada');
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Chat Jurídico</h1>

        <Card className="h-[600px] flex flex-col">
          <CardHeader className="border-b">
            <CardTitle className="text-lg">Consultoria Jurídica</CardTitle>
          </CardHeader>

          <CardContent className="flex-1 overflow-y-auto p-4 space-y-4">
            {messages.map((message) => (
              <div
                key={message.id}
                className={`flex gap-3 ${
                  message.sender === 'user' ? 'flex-row-reverse' : ''
                }`}
              >
                <div
                  className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center ${
                    message.sender === 'user'
                      ? 'bg-primary text-primary-foreground'
                      : 'bg-accent text-accent-foreground'
                  }`}
                >
                  {message.sender === 'user' ? <User size={20} /> : <Bot size={20} />}
                </div>
                <div
                  className={`max-w-[70%] p-4 rounded-lg ${
                    message.sender === 'user'
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
                placeholder="Digite sua mensagem..."
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

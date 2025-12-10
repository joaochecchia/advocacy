import { useNavigate } from 'react-router-dom';
import { Layout } from '@/components/Layout';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { MessageSquare, Clock } from 'lucide-react';

const chats = [
  {
    id: 1,
    client: 'Maria Silva',
    lastMessage: 'Obrigada pela ajuda com o contrato...',
    time: '5 min atrás',
    unread: 2,
    status: 'active',
  },
  {
    id: 2,
    client: 'João Santos',
    lastMessage: 'Quando posso marcar uma reunião?',
    time: '1 hora atrás',
    unread: 1,
    status: 'active',
  },
  {
    id: 3,
    client: 'Ana Costa',
    lastMessage: 'Perfeito, vou enviar os documentos.',
    time: '3 horas atrás',
    unread: 0,
    status: 'resolved',
  },
  {
    id: 4,
    client: 'Pedro Oliveira',
    lastMessage: 'Preciso de orientação sobre...',
    time: '1 dia atrás',
    unread: 0,
    status: 'resolved',
  },
];

export default function Chats() {
  const navigate = useNavigate();

  return (
    <Layout>
      <div className="space-y-6">
        <div>
          <h1 className="text-3xl font-bold mb-2">Gerenciar Chats</h1>
          <p className="text-muted-foreground">Visualize e responda às conversas dos clientes</p>
        </div>

        <div className="grid gap-4">
          {chats.map((chat) => (
            <Card key={chat.id} className="hover:shadow-md transition-shadow">
              <CardContent className="pt-6">
                <div className="flex items-start justify-between">
                  <div className="flex gap-4 flex-1">
                    <div className="p-3 bg-primary/10 rounded-full flex-shrink-0">
                      <MessageSquare className="text-primary" size={24} />
                    </div>
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center gap-2 mb-1">
                        <h3 className="font-semibold text-lg">{chat.client}</h3>
                        {chat.unread > 0 && (
                          <Badge variant="destructive" className="rounded-full px-2 py-0">
                            {chat.unread}
                          </Badge>
                        )}
                        <Badge
                          variant={chat.status === 'active' ? 'default' : 'secondary'}
                          className="ml-auto"
                        >
                          {chat.status === 'active' ? 'Ativo' : 'Resolvido'}
                        </Badge>
                      </div>
                      <p className="text-sm text-muted-foreground truncate mb-2">
                        {chat.lastMessage}
                      </p>
                      <div className="flex items-center gap-2 text-xs text-muted-foreground">
                        <Clock size={14} />
                        <span>{chat.time}</span>
                      </div>
                    </div>
                  </div>
                  <Button 
                    className="ml-4"
                    onClick={() => navigate(`/admin/chat-view?id=${chat.id}`)}
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

import { Layout } from '@/components/Layout';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Mail, Phone, Calendar, MoreVertical } from 'lucide-react';

const clients = [
  {
    id: 1,
    name: 'Maria Silva',
    email: 'maria.silva@email.com',
    phone: '(11) 99999-1111',
    plan: 'Premium',
    status: 'active',
    joinDate: '2023-06-15',
  },
  {
    id: 2,
    name: 'João Santos',
    email: 'joao.santos@email.com',
    phone: '(11) 99999-2222',
    plan: 'Básico',
    status: 'active',
    joinDate: '2023-08-22',
  },
  {
    id: 3,
    name: 'Ana Costa',
    email: 'ana.costa@email.com',
    phone: '(11) 99999-3333',
    plan: 'Empresarial',
    status: 'active',
    joinDate: '2023-05-10',
  },
  {
    id: 4,
    name: 'Pedro Oliveira',
    email: 'pedro.oliveira@email.com',
    phone: '(11) 99999-4444',
    plan: 'Premium',
    status: 'inactive',
    joinDate: '2023-03-05',
  },
];

export default function Clientes() {
  return (
    <Layout>
      <div className="space-y-6">
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold mb-2">Clientes</h1>
            <p className="text-muted-foreground">Gerencie todos os clientes cadastrados</p>
          </div>
          <div className="text-sm text-muted-foreground">
            Total: <span className="font-bold text-foreground">{clients.length}</span> clientes
          </div>
        </div>

        <div className="grid gap-4">
          {clients.map((client) => (
            <Card key={client.id}>
              <CardContent className="pt-6">
                <div className="flex items-start gap-4">
                  <Avatar className="w-16 h-16">
                    <AvatarFallback className="text-xl bg-primary text-primary-foreground">
                      {client.name.split(' ').map(n => n[0]).join('')}
                    </AvatarFallback>
                  </Avatar>

                  <div className="flex-1 space-y-3">
                    <div className="flex items-start justify-between">
                      <div>
                        <div className="flex items-center gap-2 mb-1">
                          <h3 className="text-lg font-semibold">{client.name}</h3>
                          <Badge variant={client.status === 'active' ? 'default' : 'secondary'}>
                            {client.status === 'active' ? 'Ativo' : 'Inativo'}
                          </Badge>
                          <Badge variant="outline">{client.plan}</Badge>
                        </div>
                      </div>
                      <Button variant="ghost" size="icon">
                        <MoreVertical size={20} />
                      </Button>
                    </div>

                    <div className="grid sm:grid-cols-3 gap-4 text-sm">
                      <div className="flex items-center gap-2">
                        <Mail size={16} className="text-muted-foreground" />
                        <span className="truncate">{client.email}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Phone size={16} className="text-muted-foreground" />
                        <span>{client.phone}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Calendar size={16} className="text-muted-foreground" />
                        <span>
                          Cliente desde{' '}
                          {new Date(client.joinDate).toLocaleDateString('pt-BR', {
                            month: 'short',
                            year: 'numeric',
                          })}
                        </span>
                      </div>
                    </div>

                    <div className="flex gap-2">
                      <Button size="sm" variant="outline">
                        Ver Perfil
                      </Button>
                      <Button size="sm" variant="outline">
                        Ver Histórico
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </Layout>
  );
}

import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Users, MessageSquare, Calendar, TrendingUp } from 'lucide-react';

const stats = [
  { title: 'Total de Clientes', value: '248', icon: Users, change: '+12%' },
  { title: 'Chats Ativos', value: '42', icon: MessageSquare, change: '+5%' },
  { title: 'Agendamentos Hoje', value: '8', icon: Calendar, change: '+2' },
  { title: 'Receita Mensal', value: 'R$ 98.952', icon: TrendingUp, change: '+18%' },
];

const recentActivities = [
  { id: 1, type: 'new_client', text: 'Novo cliente cadastrado: Maria Silva', time: '5 min atrás' },
  { id: 2, type: 'chat', text: 'Nova mensagem de João Santos', time: '15 min atrás' },
  { id: 3, type: 'schedule', text: 'Agendamento confirmado com Ana Costa', time: '1 hora atrás' },
  { id: 4, type: 'payment', text: 'Pagamento recebido: R$ 399,00', time: '2 horas atrás' },
];

export default function Dashboard() {
  return (
    <Layout>
      <div className="space-y-8">
        <div>
          <h1 className="text-3xl font-bold mb-2">Dashboard</h1>
          <p className="text-muted-foreground">Visão geral do escritório</p>
        </div>

        {/* Stats Grid */}
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
          {stats.map((stat, i) => {
            const Icon = stat.icon;
            return (
              <Card key={i}>
                <CardHeader className="flex flex-row items-center justify-between pb-2">
                  <CardTitle className="text-sm font-medium text-muted-foreground">
                    {stat.title}
                  </CardTitle>
                  <div className="p-2 bg-accent/10 rounded-full">
                    <Icon className="text-accent" size={20} />
                  </div>
                </CardHeader>
                <CardContent>
                  <div className="text-2xl font-bold">{stat.value}</div>
                  <p className="text-xs text-success mt-1">{stat.change} em relação ao mês anterior</p>
                </CardContent>
              </Card>
            );
          })}
        </div>

        <div className="grid lg:grid-cols-2 gap-6">
          {/* Atividades Recentes */}
          <Card>
            <CardHeader>
              <CardTitle>Atividades Recentes</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentActivities.map((activity) => (
                  <div key={activity.id} className="flex items-start gap-3 pb-4 border-b last:border-0 last:pb-0">
                    <div className="w-2 h-2 rounded-full bg-accent mt-2 flex-shrink-0" />
                    <div className="flex-1">
                      <p className="text-sm">{activity.text}</p>
                      <p className="text-xs text-muted-foreground mt-1">{activity.time}</p>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Estatísticas de Atendimento */}
          <Card>
            <CardHeader>
              <CardTitle>Estatísticas de Atendimento</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Taxa de Resposta</span>
                <span className="font-semibold">98%</span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Tempo Médio de Resposta</span>
                <span className="font-semibold">2.4h</span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Satisfação do Cliente</span>
                <span className="font-semibold">4.8/5.0</span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Casos Resolvidos (Mês)</span>
                <span className="font-semibold">142</span>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </Layout>
  );
}

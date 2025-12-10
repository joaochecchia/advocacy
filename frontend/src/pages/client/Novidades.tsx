import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Calendar, User as UserIcon } from 'lucide-react';

const posts = [
  {
    id: 1,
    title: 'Novas Mudanças na Legislação Trabalhista',
    content:
      'A reforma trabalhista trouxe importantes mudanças que afetam tanto empregadores quanto empregados. Neste artigo, explicamos os principais pontos...',
    author: 'Dr. João Silva',
    date: '2024-01-15',
    category: 'Trabalhista',
  },
  {
    id: 2,
    title: 'Como Proteger seu Patrimônio',
    content:
      'Proteção patrimonial é essencial para qualquer empreendedor. Descubra as melhores estratégias legais para proteger seus bens...',
    author: 'Dra. Maria Santos',
    date: '2024-01-12',
    category: 'Empresarial',
  },
  {
    id: 3,
    title: 'Direitos do Consumidor em Compras Online',
    content:
      'Conheça seus direitos ao realizar compras pela internet e saiba como proceder em caso de problemas com entregas ou produtos...',
    author: 'Dr. Pedro Costa',
    date: '2024-01-10',
    category: 'Consumidor',
  },
];

export default function Novidades() {
  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Novidades</h1>
        <p className="text-muted-foreground mb-8">
          Fique por dentro das últimas notícias e atualizações jurídicas
        </p>

        <div className="space-y-6">
          {posts.map((post) => (
            <Card key={post.id} className="hover:shadow-elegant transition-shadow">
              <CardHeader>
                <div className="flex items-start justify-between mb-2">
                  <Badge variant="secondary">{post.category}</Badge>
                  <div className="flex items-center gap-2 text-sm text-muted-foreground">
                    <Calendar size={16} />
                    <span>
                      {new Date(post.date).toLocaleDateString('pt-BR', {
                        day: '2-digit',
                        month: 'long',
                        year: 'numeric',
                      })}
                    </span>
                  </div>
                </div>
                <CardTitle className="text-2xl">{post.title}</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <p className="text-muted-foreground">{post.content}</p>
                <div className="flex items-center gap-2 text-sm">
                  <UserIcon size={16} className="text-muted-foreground" />
                  <span className="text-muted-foreground">{post.author}</span>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </Layout>
  );
}

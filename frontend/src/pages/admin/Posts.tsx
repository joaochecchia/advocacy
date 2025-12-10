import { useState } from 'react';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { Plus, Edit, Trash2 } from 'lucide-react';
import { toast } from 'sonner';

const initialPosts = [
  {
    id: 1,
    title: 'Novas Mudanças na Legislação Trabalhista',
    category: 'Trabalhista',
    date: '2024-01-15',
    status: 'published',
  },
  {
    id: 2,
    title: 'Como Proteger seu Patrimônio',
    category: 'Empresarial',
    date: '2024-01-12',
    status: 'published',
  },
];

export default function Posts() {
  const [posts, setPosts] = useState(initialPosts);
  const [showForm, setShowForm] = useState(false);
  const [editingPost, setEditingPost] = useState<typeof initialPosts[0] | null>(null);
  const [formData, setFormData] = useState({ title: '', category: '', content: '' });

  const handleDelete = (id: number) => {
    setPosts(posts.filter(p => p.id !== id));
    toast.success('Post excluído com sucesso!');
  };

  const handleEdit = (post: typeof initialPosts[0]) => {
    setEditingPost(post);
    setFormData({ title: post.title, category: post.category, content: '' });
    setShowForm(true);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (editingPost) {
      setPosts(posts.map(p => p.id === editingPost.id ? { ...p, ...formData } : p));
      toast.success('Post atualizado com sucesso!');
    } else {
      const newPost = {
        id: posts.length + 1,
        ...formData,
        date: new Date().toISOString().split('T')[0],
        status: 'published' as const,
      };
      setPosts([...posts, newPost]);
      toast.success('Post criado com sucesso!');
    }
    setShowForm(false);
    setEditingPost(null);
    setFormData({ title: '', category: '', content: '' });
  };

  return (
    <Layout>
      <div className="space-y-6">
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold mb-2">Gerenciar Posts</h1>
            <p className="text-muted-foreground">Crie e gerencie posts para o mural de novidades</p>
          </div>
          <Button onClick={() => {
            setShowForm(!showForm);
            setEditingPost(null);
            setFormData({ title: '', category: '', content: '' });
          }}>
            <Plus size={20} className="mr-2" />
            Novo Post
          </Button>
        </div>

        {showForm && (
          <Card>
            <CardHeader>
              <CardTitle>{editingPost ? 'Editar Post' : 'Criar Novo Post'}</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <Label htmlFor="title">Título</Label>
                  <Input 
                    id="title" 
                    value={formData.title}
                    onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                    required 
                  />
                </div>
                <div>
                  <Label htmlFor="category">Categoria</Label>
                  <select
                    id="category"
                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2"
                    value={formData.category}
                    onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                    required
                  >
                    <option value="">Selecione</option>
                    <option value="Trabalhista">Trabalhista</option>
                    <option value="Empresarial">Empresarial</option>
                    <option value="Consumidor">Consumidor</option>
                    <option value="Civil">Civil</option>
                  </select>
                </div>
                <div>
                  <Label htmlFor="content">Conteúdo</Label>
                  <Textarea 
                    id="content" 
                    rows={6}
                    value={formData.content}
                    onChange={(e) => setFormData({ ...formData, content: e.target.value })}
                    required 
                  />
                </div>
                <div className="flex gap-2">
                  <Button type="submit">{editingPost ? 'Atualizar' : 'Publicar'}</Button>
                  <Button type="button" variant="outline" onClick={() => {
                    setShowForm(false);
                    setEditingPost(null);
                    setFormData({ title: '', category: '', content: '' });
                  }}>
                    Cancelar
                  </Button>
                </div>
              </form>
            </CardContent>
          </Card>
        )}

        <div className="space-y-4">
          {posts.map((post) => (
            <Card key={post.id}>
              <CardContent className="pt-6">
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <div className="flex items-center gap-3 mb-2">
                      <h3 className="text-xl font-semibold">{post.title}</h3>
                      <Badge variant="secondary">{post.category}</Badge>
                      <Badge variant={post.status === 'published' ? 'default' : 'outline'}>
                        {post.status === 'published' ? 'Publicado' : 'Rascunho'}
                      </Badge>
                    </div>
                    <p className="text-sm text-muted-foreground">
                      {new Date(post.date).toLocaleDateString('pt-BR', {
                        day: '2-digit',
                        month: 'long',
                        year: 'numeric',
                      })}
                    </p>
                  </div>
                  <div className="flex gap-2">
                    <Button variant="outline" size="icon" onClick={() => handleEdit(post)}>
                      <Edit size={16} />
                    </Button>
                    <Button
                      variant="destructive"
                      size="icon"
                      onClick={() => handleDelete(post.id)}
                    >
                      <Trash2 size={16} />
                    </Button>
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

import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Scale } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { useAuth } from '@/contexts/AuthContext';
import { toast } from 'sonner';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!email || !password) {
      toast.error('Preencha todos os campos');
      return;
    }

    setLoading(true);
    
    try {
      await login(email, password);
      toast.success('Login realizado com sucesso!');
    } catch (error) {
      toast.error('Erro ao fazer login');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-hero flex items-center justify-center p-4">
      <div className="w-full max-w-md space-y-6">
        <div className="text-center">
          <div className="inline-flex items-center gap-2 mb-4">
            <Scale className="text-accent" size={40} />
            <span className="text-2xl font-bold text-white">Advocacia SaaS</span>
          </div>
          <p className="text-white/80">Acesse sua conta</p>
        </div>

        <Card>
          <CardHeader>
            <CardTitle className="text-2xl text-center">Login</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="seu@email.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="password">Senha</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>

              <Button type="submit" className="w-full" disabled={loading}>
                {loading ? 'Entrando...' : 'Entrar'}
              </Button>
            </form>

            <div className="mt-6 text-center text-sm">
              <p className="text-muted-foreground mb-2">Ainda não é cliente?</p>
              <Link to="/">
                <Button variant="outline" className="w-full">
                  Assinar Agora
                </Button>
              </Link>
            </div>

            <div className="mt-6 p-4 bg-accent/10 rounded-lg text-sm space-y-2">
              <p className="font-semibold">Dados para teste:</p>
              <p><strong>Cliente:</strong> cliente@teste.com</p>
              <p><strong>Admin:</strong> admin@teste.com</p>
              <p><strong>Senha:</strong> qualquer senha</p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

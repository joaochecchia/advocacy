import { useState } from 'react';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Camera } from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { toast } from 'sonner';

export default function Perfil() {
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    // Simula salvamento
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    toast.success('Perfil atualizado com sucesso!');
    setLoading(false);
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Meu Perfil</h1>

        <div className="grid md:grid-cols-3 gap-6">
          <Card className="md:col-span-1">
            <CardHeader>
              <CardTitle>Foto de Perfil</CardTitle>
            </CardHeader>
            <CardContent className="flex flex-col items-center space-y-4">
              <Avatar className="w-32 h-32">
                <AvatarFallback className="text-3xl bg-primary text-primary-foreground">
                  {user?.name?.[0] || 'U'}
                </AvatarFallback>
              </Avatar>
              <Button variant="outline" size="sm">
                <Camera size={16} className="mr-2" />
                Alterar Foto
              </Button>
            </CardContent>
          </Card>

          <Card className="md:col-span-2">
            <CardHeader>
              <CardTitle>Informações Pessoais</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div className="grid sm:grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="name">Nome Completo</Label>
                    <Input id="name" defaultValue={user?.name} required />
                  </div>
                  <div>
                    <Label htmlFor="email">Email</Label>
                    <Input id="email" type="email" defaultValue={user?.email} required />
                  </div>
                  <div>
                    <Label htmlFor="phone">Telefone</Label>
                    <Input id="phone" type="tel" placeholder="(11) 99999-9999" />
                  </div>
                  <div>
                    <Label htmlFor="cpf">CPF</Label>
                    <Input id="cpf" placeholder="000.000.000-00" />
                  </div>
                  <div>
                    <Label htmlFor="birthdate">Data de Nascimento</Label>
                    <Input id="birthdate" type="date" />
                  </div>
                  <div>
                    <Label htmlFor="rg">RG</Label>
                    <Input id="rg" placeholder="00.000.000-0" />
                  </div>
                </div>

                <div className="pt-4">
                  <h3 className="text-lg font-semibold mb-4">Endereço</h3>
                  <div className="grid sm:grid-cols-2 gap-4">
                    <div className="sm:col-span-2">
                      <Label htmlFor="cep">CEP</Label>
                      <Input id="cep" placeholder="00000-000" />
                    </div>
                    <div className="sm:col-span-2">
                      <Label htmlFor="address">Endereço</Label>
                      <Input id="address" placeholder="Rua, Avenida, etc." />
                    </div>
                    <div>
                      <Label htmlFor="number">Número</Label>
                      <Input id="number" placeholder="123" />
                    </div>
                    <div>
                      <Label htmlFor="complement">Complemento</Label>
                      <Input id="complement" placeholder="Apto, Bloco, etc." />
                    </div>
                    <div>
                      <Label htmlFor="neighborhood">Bairro</Label>
                      <Input id="neighborhood" />
                    </div>
                    <div>
                      <Label htmlFor="city">Cidade</Label>
                      <Input id="city" />
                    </div>
                  </div>
                </div>

                <div className="pt-4 flex gap-4">
                  <Button type="submit" disabled={loading}>
                    {loading ? 'Salvando...' : 'Salvar Alterações'}
                  </Button>
                  <Button type="button" variant="outline">
                    Cancelar
                  </Button>
                </div>
              </form>
            </CardContent>
          </Card>
        </div>
      </div>
    </Layout>
  );
}

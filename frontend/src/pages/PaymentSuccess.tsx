import { Link } from 'react-router-dom';
import { CheckCircle, ArrowRight, Mail } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';

export default function PaymentSuccess() {
  return (
    <div className="min-h-screen bg-gradient-hero flex items-center justify-center p-4">
      <Card className="max-w-2xl w-full">
        <CardContent className="pt-8 text-center space-y-6">
          <div className="inline-flex p-4 bg-success/10 rounded-full">
            <CheckCircle className="text-success" size={64} />
          </div>
          
          <div className="space-y-2">
            <h1 className="text-3xl font-bold text-primary">Pagamento Confirmado!</h1>
            <p className="text-lg text-muted-foreground">
              Sua assinatura foi ativada com sucesso
            </p>
          </div>

          <div className="bg-secondary rounded-lg p-6 space-y-4">
            <div className="flex items-start gap-3">
              <Mail className="text-accent mt-1" size={20} />
              <div className="text-left">
                <p className="font-semibold">Verifique seu email</p>
                <p className="text-sm text-muted-foreground">
                  Enviamos suas credenciais de acesso para o email cadastrado
                </p>
              </div>
            </div>
          </div>

          <div className="space-y-3">
            <h3 className="font-semibold">Próximos Passos:</h3>
            <ol className="text-left space-y-2">
              <li className="flex gap-2">
                <span className="font-bold text-accent">1.</span>
                <span>Acesse a plataforma com suas credenciais</span>
              </li>
              <li className="flex gap-2">
                <span className="font-bold text-accent">2.</span>
                <span>Complete seu perfil com suas informações</span>
              </li>
              <li className="flex gap-2">
                <span className="font-bold text-accent">3.</span>
                <span>Comece a usar nossos serviços jurídicos</span>
              </li>
            </ol>
          </div>

          <div className="flex flex-col sm:flex-row gap-4 pt-4">
            <Link to="/login" className="flex-1">
              <Button size="lg" className="w-full">
                Acessar Plataforma <ArrowRight className="ml-2" size={20} />
              </Button>
            </Link>
            <Link to="/" className="flex-1">
              <Button size="lg" variant="outline" className="w-full">
                Voltar ao Início
              </Button>
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}

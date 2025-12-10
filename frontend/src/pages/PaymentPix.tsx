import { useEffect, useState } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Copy, Check, Clock, QrCode as QrCodeIcon } from 'lucide-react';
import { toast } from 'sonner';

export default function PaymentPix() {
  const navigate = useNavigate();
  const location = useLocation();
  const { plan, amount } = location.state || { plan: 'Premium', amount: 399 };
  
  const [copied, setCopied] = useState(false);
  const [timeLeft, setTimeLeft] = useState(600); // 10 minutos
  
  const pixCode = `00020126580014br.gov.bcb.pix0136${Math.random().toString(36).substring(7)}520400005303986540${amount}.005802BR5925Advocacia SaaS6014SAO PAULO62070503***6304`;

  useEffect(() => {
    const timer = setInterval(() => {
      setTimeLeft(prev => {
        if (prev <= 1) {
          clearInterval(timer);
          navigate('/checkout');
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    // Simula confirmação de pagamento após 15 segundos (para demo)
    const paymentCheck = setTimeout(() => {
      toast.success('Pagamento confirmado!');
      setTimeout(() => navigate('/payment-success'), 1500);
    }, 15000);

    return () => {
      clearInterval(timer);
      clearTimeout(paymentCheck);
    };
  }, [navigate]);

  const copyToClipboard = () => {
    navigator.clipboard.writeText(pixCode);
    setCopied(true);
    toast.success('Código copiado!');
    setTimeout(() => setCopied(false), 3000);
  };

  const minutes = Math.floor(timeLeft / 60);
  const seconds = timeLeft % 60;

  return (
    <div className="min-h-screen bg-secondary py-8">
      <div className="container mx-auto px-4 max-w-2xl">
        <Card>
          <CardHeader className="text-center">
            <CardTitle className="text-2xl">Pagamento via PIX</CardTitle>
            <p className="text-muted-foreground mt-2">
              Plano {plan} - R$ {amount},00
            </p>
          </CardHeader>
          <CardContent className="space-y-6">
            {/* Timer */}
            <div className="flex items-center justify-center gap-2 text-destructive">
              <Clock size={20} />
              <span className="font-semibold">
                Expira em: {minutes}:{seconds.toString().padStart(2, '0')}
              </span>
            </div>

            {/* QR Code */}
            <div className="bg-white p-8 rounded-lg flex items-center justify-center">
              <div className="bg-gradient-to-br from-primary to-accent p-6 rounded-lg">
                <QrCodeIcon size={200} className="text-white" />
              </div>
            </div>

            {/* Instruções */}
            <div className="space-y-3">
              <p className="font-semibold text-center">Como pagar:</p>
              <ol className="space-y-2 text-sm">
                <li className="flex gap-2">
                  <span className="font-bold">1.</span>
                  <span>Abra o app do seu banco</span>
                </li>
                <li className="flex gap-2">
                  <span className="font-bold">2.</span>
                  <span>Escolha pagar via PIX</span>
                </li>
                <li className="flex gap-2">
                  <span className="font-bold">3.</span>
                  <span>Escaneie o QR Code ou copie o código abaixo</span>
                </li>
              </ol>
            </div>

            {/* Código PIX */}
            <div className="space-y-2">
              <Label>Código PIX Copia e Cola</Label>
              <div className="flex gap-2">
                <Input 
                  value={pixCode} 
                  readOnly 
                  className="font-mono text-sm"
                />
                <Button 
                  onClick={copyToClipboard}
                  variant="outline"
                  size="icon"
                  className="flex-shrink-0"
                >
                  {copied ? <Check size={20} /> : <Copy size={20} />}
                </Button>
              </div>
            </div>

            <div className="bg-accent/10 border border-accent/20 rounded-lg p-4 text-sm text-center">
              <p className="font-semibold mb-1">Aguardando pagamento...</p>
              <p className="text-muted-foreground">
                Após a confirmação, você receberá acesso imediato à plataforma
              </p>
            </div>

            <div className="flex gap-4">
              <Link to="/checkout" className="flex-1">
                <Button variant="outline" className="w-full">
                  Voltar
                </Button>
              </Link>
              <Button 
                className="flex-1"
                onClick={() => {
                  toast.info('Verificando pagamento...');
                }}
              >
                Já paguei
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

function Label({ children }: { children: React.ReactNode }) {
  return <label className="text-sm font-medium">{children}</label>;
}

function Input(props: React.InputHTMLAttributes<HTMLInputElement>) {
  return (
    <input
      {...props}
      className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm"
    />
  );
}

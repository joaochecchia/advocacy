import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { CreditCard, Lock } from 'lucide-react';
import { toast } from 'sonner';

export default function PaymentCreditCard() {
  const navigate = useNavigate();
  const [processing, setProcessing] = useState(false);
  const [cardData, setCardData] = useState({
    number: '',
    name: '',
    expiry: '',
    cvv: '',
  });

  const formatCardNumber = (value: string) => {
    const numbers = value.replace(/\D/g, '');
    const formatted = numbers.match(/.{1,4}/g)?.join(' ') || '';
    return formatted.substring(0, 19);
  };

  const formatExpiry = (value: string) => {
    const numbers = value.replace(/\D/g, '');
    if (numbers.length >= 2) {
      return `${numbers.substring(0, 2)}/${numbers.substring(2, 4)}`;
    }
    return numbers;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setProcessing(true);

    setTimeout(() => {
      setProcessing(false);
      toast.success('Pagamento processado com sucesso!');
      navigate('/payment-success');
    }, 2000);
  };

  return (
    <div className="min-h-screen bg-gradient-subtle flex items-center justify-center p-4">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle className="flex items-center gap-2 text-2xl">
            <CreditCard className="text-primary" />
            Pagamento com Cartão
          </CardTitle>
          <p className="text-sm text-muted-foreground mt-2">
            Insira os dados do seu cartão de crédito
          </p>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <Label htmlFor="cardNumber">Número do Cartão</Label>
              <Input
                id="cardNumber"
                type="text"
                placeholder="1234 5678 9012 3456"
                value={cardData.number}
                onChange={(e) =>
                  setCardData({ ...cardData, number: formatCardNumber(e.target.value) })
                }
                maxLength={19}
                required
              />
            </div>

            <div>
              <Label htmlFor="cardName">Nome no Cartão</Label>
              <Input
                id="cardName"
                type="text"
                placeholder="NOME COMO ESTÁ NO CARTÃO"
                value={cardData.name}
                onChange={(e) =>
                  setCardData({ ...cardData, name: e.target.value.toUpperCase() })
                }
                required
              />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="expiry">Validade</Label>
                <Input
                  id="expiry"
                  type="text"
                  placeholder="MM/AA"
                  value={cardData.expiry}
                  onChange={(e) =>
                    setCardData({ ...cardData, expiry: formatExpiry(e.target.value) })
                  }
                  maxLength={5}
                  required
                />
              </div>
              <div>
                <Label htmlFor="cvv">CVV</Label>
                <Input
                  id="cvv"
                  type="text"
                  placeholder="123"
                  value={cardData.cvv}
                  onChange={(e) =>
                    setCardData({ ...cardData, cvv: e.target.value.replace(/\D/g, '') })
                  }
                  maxLength={4}
                  required
                />
              </div>
            </div>

            <div className="bg-muted p-4 rounded-lg flex items-start gap-2">
              <Lock size={16} className="text-primary mt-0.5 flex-shrink-0" />
              <p className="text-xs text-muted-foreground">
                Seus dados estão protegidos com criptografia de ponta a ponta. Não armazenamos
                informações do cartão.
              </p>
            </div>

            <Button type="submit" className="w-full" disabled={processing}>
              {processing ? 'Processando...' : 'Confirmar Pagamento'}
            </Button>

            <Button
              type="button"
              variant="outline"
              className="w-full"
              onClick={() => navigate('/checkout')}
            >
              Voltar
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}

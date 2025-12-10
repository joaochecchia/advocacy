import { useState } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { ArrowLeft, CreditCard, QrCode, Check } from 'lucide-react';
import { toast } from 'sonner';

const plans = {
  'Básico': { price: 199, features: ['Chat jurídico ilimitado', 'Até 2 agendamentos/mês'] },
  'Premium': { price: 399, features: ['Tudo do Básico', 'Agendamentos ilimitados', 'Suporte 24/7'] },
  'Empresarial': { price: 799, features: ['Tudo do Premium', 'Múltiplos usuários', 'Advogado dedicado'] },
};

export default function Checkout() {
  const navigate = useNavigate();
  const location = useLocation();
  const [selectedPlan, setSelectedPlan] = useState(location.state?.selectedPlan || 'Premium');
  const [paymentMethod, setPaymentMethod] = useState('pix');
  const [loading, setLoading] = useState(false);

  const plan = plans[selectedPlan as keyof typeof plans];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    // Simula processamento
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    if (paymentMethod === 'pix') {
      navigate('/payment-pix', { state: { plan: selectedPlan, amount: plan.price } });
    } else if (paymentMethod === 'cartao') {
      navigate('/payment-credit-card', { state: { plan: selectedPlan, amount: plan.price } });
    }
    
    setLoading(false);
  };

  return (
    <div className="min-h-screen bg-secondary py-8">
      <div className="container mx-auto px-4 max-w-6xl">
        <Link to="/" className="inline-flex items-center gap-2 text-primary hover:text-primary/80 mb-6">
          <ArrowLeft size={20} />
          <span>Voltar</span>
        </Link>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Formulário */}
          <div className="lg:col-span-2">
            <Card>
              <CardHeader>
                <CardTitle className="text-2xl">Finalizar Assinatura</CardTitle>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="space-y-6">
                  {/* Seleção de Plano */}
                  <div className="space-y-4">
                    <Label className="text-lg">Escolha seu Plano</Label>
                    <RadioGroup value={selectedPlan} onValueChange={setSelectedPlan}>
                      {Object.entries(plans).map(([name, details]) => (
                        <div key={name} className="flex items-center space-x-3 border border-border rounded-lg p-4 hover:border-primary transition-colors">
                          <RadioGroupItem value={name} id={name} />
                          <Label htmlFor={name} className="flex-1 cursor-pointer">
                            <div className="font-semibold">{name}</div>
                            <div className="text-sm text-muted-foreground">
                              R$ {details.price}/mês
                            </div>
                          </Label>
                        </div>
                      ))}
                    </RadioGroup>
                  </div>

                  {/* Dados Pessoais */}
                  <div className="space-y-4">
                    <Label className="text-lg">Dados Pessoais</Label>
                    <div className="grid sm:grid-cols-2 gap-4">
                      <div>
                        <Label htmlFor="name">Nome Completo</Label>
                        <Input id="name" required />
                      </div>
                      <div>
                        <Label htmlFor="email">Email</Label>
                        <Input id="email" type="email" required />
                      </div>
                      <div>
                        <Label htmlFor="phone">Telefone</Label>
                        <Input id="phone" type="tel" required />
                      </div>
                      <div>
                        <Label htmlFor="cpf">CPF</Label>
                        <Input id="cpf" required />
                      </div>
                    </div>
                  </div>

                   {/* Método de Pagamento */}
                  <div className="space-y-4">
                    <Label className="text-lg">Método de Pagamento</Label>
                    <RadioGroup value={paymentMethod} onValueChange={setPaymentMethod}>
                      <div className="flex items-center space-x-3 border border-border rounded-lg p-4">
                        <RadioGroupItem value="pix" id="pix" />
                        <Label htmlFor="pix" className="flex items-center gap-2 flex-1 cursor-pointer">
                          <QrCode size={20} />
                          <span>PIX (Aprovação instantânea)</span>
                        </Label>
                      </div>
                      <div className="flex items-center space-x-3 border border-border rounded-lg p-4">
                        <RadioGroupItem value="cartao" id="cartao" />
                        <Label htmlFor="cartao" className="flex items-center gap-2 flex-1 cursor-pointer">
                          <CreditCard size={20} />
                          <span>Cartão de Crédito</span>
                        </Label>
                      </div>
                    </RadioGroup>
                  </div>

                  <Button type="submit" size="lg" className="w-full" disabled={loading}>
                    {loading ? 'Processando...' : 'Finalizar Pagamento'}
                  </Button>
                </form>
              </CardContent>
            </Card>
          </div>

          {/* Resumo do Pedido */}
          <div className="lg:col-span-1">
            <Card className="sticky top-8">
              <CardHeader>
                <CardTitle>Resumo do Pedido</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <div className="font-semibold mb-2">Plano {selectedPlan}</div>
                  <ul className="space-y-2 text-sm">
                    {plan.features.map((feature, i) => (
                      <li key={i} className="flex items-start gap-2">
                        <Check size={16} className="text-success mt-0.5 flex-shrink-0" />
                        <span>{feature}</span>
                      </li>
                    ))}
                  </ul>
                </div>
                
                <div className="border-t pt-4 space-y-2">
                  <div className="flex justify-between">
                    <span>Subtotal</span>
                    <span>R$ {plan.price},00</span>
                  </div>
                  <div className="flex justify-between font-bold text-lg">
                    <span>Total</span>
                    <span>R$ {plan.price},00</span>
                  </div>
                </div>

                <div className="bg-accent/10 border border-accent/20 rounded-lg p-4 text-sm">
                  <p className="font-semibold mb-2">Precisa de ajuda?</p>
                  <p className="text-muted-foreground mb-2">
                    Fale conosco no WhatsApp
                  </p>
                  <a href="https://wa.me/5511999999999" target="_blank" rel="noopener noreferrer">
                    <Button variant="outline" size="sm" className="w-full">
                      Falar no WhatsApp
                    </Button>
                  </a>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}

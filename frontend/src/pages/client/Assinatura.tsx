import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { CreditCard, Calendar, CheckCircle, XCircle } from 'lucide-react';

const currentPlan = {
  name: 'Premium',
  price: 399,
  status: 'active',
  nextBilling: '2024-02-15',
  paymentMethod: 'Cartão •••• 4242',
};

const invoices = [
  { id: 1, date: '2024-01-15', amount: 399, status: 'paid' },
  { id: 2, date: '2023-12-15', amount: 399, status: 'paid' },
  { id: 3, date: '2023-11-15', amount: 399, status: 'paid' },
];

export default function Assinatura() {
  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Minha Assinatura</h1>

        <div className="grid md:grid-cols-2 gap-6 mb-8">
          <Card>
            <CardHeader>
              <CardTitle>Plano Atual</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div>
                <div className="flex items-center justify-between mb-2">
                  <span className="text-2xl font-bold">{currentPlan.name}</span>
                  <Badge variant={currentPlan.status === 'active' ? 'default' : 'destructive'}>
                    {currentPlan.status === 'active' ? 'Ativo' : 'Inativo'}
                  </Badge>
                </div>
                <p className="text-3xl font-bold text-primary">
                  R$ {currentPlan.price}
                  <span className="text-base text-muted-foreground">/mês</span>
                </p>
              </div>

              <div className="space-y-2 text-sm">
                <div className="flex items-center gap-2">
                  <Calendar size={16} className="text-muted-foreground" />
                  <span>
                    Próxima cobrança:{' '}
                    {new Date(currentPlan.nextBilling).toLocaleDateString('pt-BR')}
                  </span>
                </div>
                <div className="flex items-center gap-2">
                  <CreditCard size={16} className="text-muted-foreground" />
                  <span>{currentPlan.paymentMethod}</span>
                </div>
              </div>

              <div className="pt-4 space-y-2">
                <Button variant="outline" className="w-full">
                  Alterar Plano
                </Button>
                <Button variant="destructive" className="w-full">
                  Cancelar Assinatura
                </Button>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Método de Pagamento</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="border border-border rounded-lg p-4 flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="p-2 bg-accent/10 rounded">
                    <CreditCard className="text-accent" size={24} />
                  </div>
                  <div>
                    <p className="font-semibold">Cartão de Crédito</p>
                    <p className="text-sm text-muted-foreground">
                      {currentPlan.paymentMethod}
                    </p>
                  </div>
                </div>
                <Badge variant="secondary">Principal</Badge>
              </div>
              <Button variant="outline" className="w-full">
                Alterar Método de Pagamento
              </Button>
            </CardContent>
          </Card>
        </div>

        <Card>
          <CardHeader>
            <CardTitle>Histórico de Pagamentos</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {invoices.map((invoice) => (
                <div
                  key={invoice.id}
                  className="flex items-center justify-between border-b border-border pb-4 last:border-0 last:pb-0"
                >
                  <div className="flex items-center gap-3">
                    {invoice.status === 'paid' ? (
                      <CheckCircle className="text-success" size={20} />
                    ) : (
                      <XCircle className="text-destructive" size={20} />
                    )}
                    <div>
                      <p className="font-semibold">
                        {new Date(invoice.date).toLocaleDateString('pt-BR', {
                          day: '2-digit',
                          month: 'long',
                          year: 'numeric',
                        })}
                      </p>
                      <p className="text-sm text-muted-foreground">
                        {invoice.status === 'paid' ? 'Pago' : 'Pendente'}
                      </p>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="font-semibold">R$ {invoice.amount},00</p>
                    <Button variant="link" size="sm" className="h-auto p-0">
                      Ver nota fiscal
                    </Button>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </Layout>
  );
}

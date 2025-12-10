import { useState } from 'react';
import { Layout } from '@/components/Layout';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Calendar } from '@/components/ui/calendar';
import { toast } from 'sonner';

export default function Agendamento() {
  const [date, setDate] = useState<Date | undefined>(new Date());
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    // Simula envio
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    toast.success('Agendamento solicitado com sucesso!');
    setLoading(false);
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-6">Agendar Reunião</h1>
        <p className="text-muted-foreground mb-8">
          Solicite um agendamento para consultoria presencial ou por videochamada
        </p>

        <div className="grid md:grid-cols-2 gap-8">
          <Card>
            <CardHeader>
              <CardTitle>Selecione a Data</CardTitle>
            </CardHeader>
            <CardContent className="flex justify-center">
              <Calendar
                mode="single"
                selected={date}
                onSelect={setDate}
                className="rounded-md border"
              />
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Informações da Reunião</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <Label htmlFor="subject">Assunto</Label>
                  <Input
                    id="subject"
                    placeholder="Ex: Consultoria sobre contratos"
                    required
                  />
                </div>

                <div>
                  <Label htmlFor="time">Horário Preferencial</Label>
                  <select
                    id="time"
                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2"
                    required
                  >
                    <option value="">Selecione um horário</option>
                    <option value="09:00">09:00</option>
                    <option value="10:00">10:00</option>
                    <option value="11:00">11:00</option>
                    <option value="14:00">14:00</option>
                    <option value="15:00">15:00</option>
                    <option value="16:00">16:00</option>
                  </select>
                </div>

                <div>
                  <Label htmlFor="type">Tipo de Reunião</Label>
                  <select
                    id="type"
                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2"
                    required
                  >
                    <option value="">Selecione o tipo</option>
                    <option value="presencial">Presencial</option>
                    <option value="online">Videochamada</option>
                  </select>
                </div>

                <div>
                  <Label htmlFor="description">Descrição (opcional)</Label>
                  <Textarea
                    id="description"
                    placeholder="Descreva brevemente o motivo da reunião..."
                    rows={4}
                  />
                </div>

                <Button type="submit" className="w-full" disabled={loading}>
                  {loading ? 'Enviando...' : 'Solicitar Agendamento'}
                </Button>
              </form>
            </CardContent>
          </Card>
        </div>
      </div>
    </Layout>
  );
}

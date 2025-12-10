import { useState } from 'react';
import { Layout } from '@/components/Layout';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Calendar, Clock, Video, MapPin, Check, X } from 'lucide-react';
import { toast } from 'sonner';

const initialAppointments = [
  {
    id: 1,
    client: 'Maria Silva',
    subject: 'Consultoria sobre contratos',
    date: '2024-01-20',
    time: '10:00',
    type: 'online',
    status: 'pending',
  },
  {
    id: 2,
    client: 'João Santos',
    subject: 'Revisão de documentos',
    date: '2024-01-20',
    time: '14:00',
    type: 'presencial',
    status: 'pending',
  },
  {
    id: 3,
    client: 'Ana Costa',
    subject: 'Consultoria trabalhista',
    date: '2024-01-21',
    time: '09:00',
    type: 'online',
    status: 'approved',
  },
];

export default function Agendamentos() {
  const [appointments, setAppointments] = useState(initialAppointments);

  const handleApprove = (id: number) => {
    setAppointments(appointments.map(a => 
      a.id === id ? { ...a, status: 'approved' } : a
    ));
    toast.success('Agendamento aprovado!');
  };

  const handleReject = (id: number) => {
    setAppointments(appointments.map(a => 
      a.id === id ? { ...a, status: 'rejected' } : a
    ));
    toast.error('Agendamento rejeitado');
  };

  return (
    <Layout>
      <div className="space-y-6">
        <div>
          <h1 className="text-3xl font-bold mb-2">Agendamentos</h1>
          <p className="text-muted-foreground">Gerencie os agendamentos dos clientes</p>
        </div>

        <div className="grid gap-4">
          {appointments.map((appointment) => (
            <Card key={appointment.id}>
              <CardContent className="pt-6">
                <div className="flex items-start justify-between gap-4">
                  <div className="flex-1 space-y-3">
                    <div>
                      <div className="flex items-center gap-2 mb-1">
                        <h3 className="text-lg font-semibold">{appointment.client}</h3>
                        <Badge
                          variant={
                            appointment.status === 'approved'
                              ? 'default'
                              : appointment.status === 'rejected'
                              ? 'destructive'
                              : 'secondary'
                          }
                        >
                          {appointment.status === 'approved'
                            ? 'Aprovado'
                            : appointment.status === 'rejected'
                            ? 'Rejeitado'
                            : 'Pendente'}
                        </Badge>
                      </div>
                      <p className="text-muted-foreground">{appointment.subject}</p>
                    </div>

                    <div className="flex flex-wrap gap-4 text-sm">
                      <div className="flex items-center gap-2">
                        <Calendar size={16} className="text-muted-foreground" />
                        <span>
                          {new Date(appointment.date).toLocaleDateString('pt-BR', {
                            day: '2-digit',
                            month: 'long',
                            year: 'numeric',
                          })}
                        </span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Clock size={16} className="text-muted-foreground" />
                        <span>{appointment.time}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        {appointment.type === 'online' ? (
                          <>
                            <Video size={16} className="text-muted-foreground" />
                            <span>Videochamada</span>
                          </>
                        ) : (
                          <>
                            <MapPin size={16} className="text-muted-foreground" />
                            <span>Presencial</span>
                          </>
                        )}
                      </div>
                    </div>
                  </div>

                  {appointment.status === 'pending' && (
                    <div className="flex gap-2">
                      <Button
                        size="icon"
                        variant="outline"
                        className="text-success hover:bg-success/10"
                        onClick={() => handleApprove(appointment.id)}
                      >
                        <Check size={20} />
                      </Button>
                      <Button
                        size="icon"
                        variant="outline"
                        className="text-destructive hover:bg-destructive/10"
                        onClick={() => handleReject(appointment.id)}
                      >
                        <X size={20} />
                      </Button>
                    </div>
                  )}
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </Layout>
  );
}

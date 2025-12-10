import { Link } from 'react-router-dom';
import { 
  Scale, 
  Shield, 
  Clock, 
  Users, 
  CheckCircle, 
  MessageSquare,
  Calendar,
  FileText,
  ArrowRight,
  ChevronDown
} from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { 
  Accordion, 
  AccordionContent, 
  AccordionItem, 
  AccordionTrigger 
} from '@/components/ui/accordion';

const plans = [
  {
    name: 'Básico',
    price: 'R$ 199',
    period: '/mês',
    features: [
      'Chat jurídico ilimitado',
      'Até 2 agendamentos/mês',
      'Acesso ao portal',
      'Suporte por email',
    ],
  },
  {
    name: 'Premium',
    price: 'R$ 399',
    period: '/mês',
    popular: true,
    features: [
      'Tudo do Básico',
      'Agendamentos ilimitados',
      'Consultoria prioritária',
      'Suporte 24/7',
      'Documentos personalizados',
    ],
  },
  {
    name: 'Empresarial',
    price: 'R$ 799',
    period: '/mês',
    features: [
      'Tudo do Premium',
      'Múltiplos usuários',
      'Advogado dedicado',
      'Relatórios personalizados',
      'Integração API',
    ],
  },
];

const testimonials = [
  {
    name: 'Maria Silva',
    role: 'Empresária',
    content: 'A plataforma revolucionou a forma como gerencio as questões jurídicas da minha empresa.',
  },
  {
    name: 'João Santos',
    role: 'Empreendedor',
    content: 'Atendimento rápido e profissional. Resolveram meu caso com muita eficiência.',
  },
  {
    name: 'Ana Costa',
    role: 'Gestora',
    content: 'Excelente custo-benefício. Tenho todo suporte jurídico que preciso.',
  },
];

const faqItems = [
  {
    question: 'Como funciona a assinatura?',
    answer: 'Você escolhe o plano ideal, realiza o pagamento e tem acesso imediato ao portal do cliente. Pode cancelar quando quiser.',
  },
  {
    question: 'Posso trocar de plano depois?',
    answer: 'Sim! Você pode fazer upgrade ou downgrade do seu plano a qualquer momento através do painel de assinatura.',
  },
  {
    question: 'Qual o prazo de resposta do chat?',
    answer: 'No plano Básico, até 24h. No Premium e Empresarial, priorizamos seu atendimento com respostas em até 4h.',
  },
  {
    question: 'Preciso assinar um contrato longo?',
    answer: 'Não! Nossos planos são mensais e você pode cancelar a qualquer momento sem multas.',
  },
];

export default function LandingPage() {
  return (
    <div className="min-h-screen">
      {/* Header */}
      <header className="bg-background border-b border-border sticky top-0 z-30">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-2">
            <Scale className="text-primary" size={32} />
            <span className="text-xl font-bold text-primary">Advocacia SaaS</span>
          </div>
          <div className="flex gap-4">
            <Link to="/login">
              <Button variant="outline">Já sou cliente</Button>
            </Link>
            <Link to="/checkout">
              <Button>Começar Agora</Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="relative bg-gradient-hero text-white py-24 overflow-hidden">
        <div className="container mx-auto px-4">
          <div className="max-w-3xl mx-auto text-center">
            <h1 className="text-5xl md:text-6xl font-bold mb-6 animate-fade-in">
              Consultoria Jurídica <span className="text-accent">Inteligente</span>
            </h1>
            <p className="text-xl md:text-2xl mb-8 text-primary-foreground/90 animate-fade-in">
              Tenha um escritório de advocacia completo ao seu alcance. 
              Resolva questões jurídicas de forma rápida e profissional.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center animate-fade-in">
              <Link to="/checkout">
                <Button size="lg" className="bg-accent hover:bg-accent/90 text-accent-foreground">
                  Começar Agora <ArrowRight className="ml-2" size={20} />
                </Button>
              </Link>
              <a href="https://wa.me/5511999999999" target="_blank" rel="noopener noreferrer">
                <Button size="lg" variant="outline" className="bg-white/10 hover:bg-white/20 text-white border-white">
                  Falar no WhatsApp
                </Button>
              </a>
            </div>
          </div>
        </div>
        <ChevronDown className="absolute bottom-8 left-1/2 -translate-x-1/2 animate-bounce text-white/50" size={32} />
      </section>

      {/* Benefits */}
      <section className="py-20 bg-background">
        <div className="container mx-auto px-4">
          <h2 className="text-4xl font-bold text-center mb-12 text-primary">Por que escolher-nos?</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {[
              { icon: MessageSquare, title: 'Chat Jurídico', desc: 'Tire suas dúvidas em tempo real' },
              { icon: Calendar, title: 'Agendamentos', desc: 'Marque reuniões facilmente' },
              { icon: FileText, title: 'Documentos', desc: 'Acesse seus arquivos a qualquer hora' },
              { icon: Shield, title: 'Segurança', desc: 'Seus dados totalmente protegidos' },
            ].map((item, i) => {
              const Icon = item.icon;
              return (
                <Card key={i} className="border-border hover:shadow-elegant transition-all">
                  <CardContent className="pt-6 text-center">
                    <div className="mb-4 inline-flex p-4 rounded-full bg-accent/10">
                      <Icon className="text-accent" size={32} />
                    </div>
                    <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
                    <p className="text-muted-foreground">{item.desc}</p>
                  </CardContent>
                </Card>
              );
            })}
          </div>
        </div>
      </section>

      {/* Plans */}
      <section className="py-20 bg-secondary" id="planos">
        <div className="container mx-auto px-4">
          <h2 className="text-4xl font-bold text-center mb-4 text-primary">Escolha seu Plano</h2>
          <p className="text-center text-muted-foreground mb-12">Selecione a melhor opção para suas necessidades</p>
          
          <div className="grid md:grid-cols-3 gap-8 max-w-6xl mx-auto">
            {plans.map((plan, i) => (
              <Card 
                key={i} 
                className={cn(
                  "relative border-2 hover:shadow-elegant transition-all",
                  plan.popular ? "border-accent scale-105" : "border-border"
                )}
              >
                {plan.popular && (
                  <div className="absolute -top-4 left-1/2 -translate-x-1/2">
                    <span className="bg-accent text-accent-foreground px-4 py-1 rounded-full text-sm font-semibold">
                      Mais Popular
                    </span>
                  </div>
                )}
                <CardContent className="pt-8">
                  <h3 className="text-2xl font-bold mb-2">{plan.name}</h3>
                  <div className="mb-6">
                    <span className="text-4xl font-bold text-primary">{plan.price}</span>
                    <span className="text-muted-foreground">{plan.period}</span>
                  </div>
                  <ul className="space-y-3 mb-8">
                    {plan.features.map((feature, j) => (
                      <li key={j} className="flex items-start gap-2">
                        <CheckCircle className="text-accent flex-shrink-0 mt-0.5" size={20} />
                        <span>{feature}</span>
                      </li>
                    ))}
                  </ul>
                  <Link to="/checkout" state={{ selectedPlan: plan.name }}>
                    <Button className="w-full" variant={plan.popular ? "default" : "outline"}>
                      Assinar Agora
                    </Button>
                  </Link>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="py-20 bg-background">
        <div className="container mx-auto px-4">
          <h2 className="text-4xl font-bold text-center mb-12 text-primary">O que dizem nossos clientes</h2>
          <div className="grid md:grid-cols-3 gap-8 max-w-5xl mx-auto">
            {testimonials.map((testimonial, i) => (
              <Card key={i} className="border-border">
                <CardContent className="pt-6">
                  <p className="mb-4 italic text-muted-foreground">"{testimonial.content}"</p>
                  <div>
                    <p className="font-semibold">{testimonial.name}</p>
                    <p className="text-sm text-muted-foreground">{testimonial.role}</p>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* FAQ */}
      <section className="py-20 bg-secondary">
        <div className="container mx-auto px-4">
          <h2 className="text-4xl font-bold text-center mb-12 text-primary">Perguntas Frequentes</h2>
          <div className="max-w-3xl mx-auto">
            <Accordion type="single" collapsible className="space-y-4">
              {faqItems.map((item, i) => (
                <AccordionItem key={i} value={`item-${i}`} className="border border-border rounded-lg px-4 bg-background">
                  <AccordionTrigger className="text-left font-semibold">
                    {item.question}
                  </AccordionTrigger>
                  <AccordionContent className="text-muted-foreground">
                    {item.answer}
                  </AccordionContent>
                </AccordionItem>
              ))}
            </Accordion>
          </div>
        </div>
      </section>

      {/* CTA Final */}
      <section className="py-20 bg-gradient-hero text-white">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-4xl font-bold mb-6">Pronto para começar?</h2>
          <p className="text-xl mb-8 text-primary-foreground/90">
            Junte-se a centenas de clientes satisfeitos
          </p>
          <Link to="/checkout">
            <Button size="lg" className="bg-accent hover:bg-accent/90 text-accent-foreground">
              Começar Agora <ArrowRight className="ml-2" size={20} />
            </Button>
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-primary text-primary-foreground py-8">
        <div className="container mx-auto px-4 text-center">
          <p>&copy; 2024 Advocacia SaaS. Todos os direitos reservados.</p>
        </div>
      </footer>
    </div>
  );
}

function cn(...inputs: any[]) {
  return inputs.filter(Boolean).join(' ');
}

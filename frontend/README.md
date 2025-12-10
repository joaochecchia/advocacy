# 🏛️ Advocacia SaaS - Frontend MVP

Sistema completo de advocacia online desenvolvido em React com as melhores práticas atuais.

## 📋 Visão Geral

Plataforma SaaS completa para escritórios de advocacia com portais distintos para clientes e administradores.

### ✨ Funcionalidades Principais

#### 🌐 Público
- **Landing Page** profissional com seções de vendas
- **Checkout** completo com múltiplos métodos de pagamento
- **Pagamento PIX** com QR Code e código copia-e-cola
- **Tela de sucesso** pós-pagamento
- **Login unificado** com redirecionamento automático

#### 👤 Portal do Cliente
- **Chat Jurídico** - Consultoria em tempo real
- **Novidades** - Mural de posts e artigos
- **Agendamento** - Marque reuniões presenciais ou online
- **Assinatura** - Gerencie seu plano e pagamentos
- **Perfil** - Atualize seus dados pessoais

#### 👨‍💼 Portal Administrativo
- **Dashboard** - Métricas e estatísticas
- **Posts** - CRUD completo de publicações
- **Chats** - Gerenciamento de conversas
- **Agendamentos** - Aprovação/rejeição de reuniões
- **Clientes** - Lista completa de usuários

## 🎨 Design System

- **Cores**: Azul marinho profissional (#1a2645) + Dourado elegante (#f59e0b)
- **Componentes**: shadcn/ui totalmente customizados
- **Responsivo**: Mobile-first, 100% adaptável
- **Animações**: Transições suaves e modernas

## 🏗️ Arquitetura

### Tecnologias
- **React 18** com TypeScript
- **Vite** para build ultra-rápido
- **Tailwind CSS** com design system customizado
- **React Router** para navegação
- **Context API** para estado global
- **shadcn/ui** para componentes

### Estrutura de Pastas
```
src/
├── components/
│   ├── ui/              # Componentes shadcn
│   ├── Layout.tsx       # Layout principal
│   ├── Sidebar.tsx      # Navegação lateral
│   └── ProtectedRoute.tsx
├── contexts/
│   └── AuthContext.tsx  # Gerenciamento de autenticação
├── pages/
│   ├── LandingPage.tsx
│   ├── Checkout.tsx
│   ├── PaymentPix.tsx
│   ├── PaymentSuccess.tsx
│   ├── Login.tsx
│   ├── client/          # Páginas do cliente
│   │   ├── Chat.tsx
│   │   ├── Novidades.tsx
│   │   ├── Agendamento.tsx
│   │   ├── Assinatura.tsx
│   │   └── Perfil.tsx
│   └── admin/           # Páginas do admin
│       ├── Dashboard.tsx
│       ├── Posts.tsx
│       ├── Chats.tsx
│       ├── Agendamentos.tsx
│       └── Clientes.tsx
└── index.css            # Design system global
```

## 🚀 Como Usar

### Instalação
```bash
npm install
```

### Desenvolvimento
```bash
npm run dev
```

### Build para Produção
```bash
npm run build
```

### Preview da Build
```bash
npm run preview
```

## 🔐 Autenticação (Demo)

O sistema usa autenticação simulada baseada no email:

- **Cliente**: Use qualquer email sem "admin" (ex: `cliente@teste.com`)
- **Admin**: Use email com "admin" (ex: `admin@teste.com`)
- **Senha**: Qualquer valor (autenticação é mockada)

## 📱 Responsividade

- **Mobile**: Menu hambúrguer, layout otimizado
- **Tablet**: Layouts adaptáveis
- **Desktop**: Sidebar fixa, máximo aproveitamento

## 🎯 Padrões Implementados

✅ **Context API** para estado global  
✅ **Protected Routes** por tipo de usuário  
✅ **Componentização** modular e reutilizável  
✅ **Design System** completo e consistente  
✅ **TypeScript** para type-safety  
✅ **SEO** otimizado com meta tags  
✅ **Accessibility** com ARIA labels  
✅ **Performance** otimizada

## 🔄 Fluxos de Usuário

### Fluxo de Compra
1. Landing Page → Checkout
2. Seleção de Plano + Dados
3. Escolha de Pagamento (PIX/Cartão/Boleto)
4. Se PIX: Tela com QR Code
5. Confirmação → Tela de Sucesso
6. Acesso ao Login

### Fluxo de Autenticação
1. Login com email e senha
2. Sistema detecta tipo de usuário
3. Redireciona automaticamente:
   - Cliente → `/client/chat`
   - Admin → `/admin/dashboard`

## 🎨 Customização

### Cores
Edite `src/index.css` para alterar o esquema de cores:
```css
:root {
  --primary: 220 85% 20%;    /* Azul principal */
  --accent: 45 90% 55%;      /* Dourado */
  /* ... */
}
```

### Componentes
Todos os componentes shadcn podem ser customizados em `src/components/ui/`

## 📦 Deploy

O projeto está pronto para deploy em:
- Vercel (recomendado)
- Netlify
- GitHub Pages
- Qualquer hosting estático

## 🛠️ Próximos Passos (Backend)

Para integração com backend real:

1. Substituir Context API por chamadas à API real
2. Implementar endpoints `/api/*` conforme documentação
3. Adicionar autenticação JWT
4. Conectar pagamentos com gateway real
5. Implementar WebSocket para chat em tempo real

## 📄 Licença

Este é um projeto MVP para demonstração. Todos os direitos reservados.

---

**Desenvolvido com as melhores práticas de React e TypeScript** ⚛️

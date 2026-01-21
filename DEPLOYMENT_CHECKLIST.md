# ✅ Checklist de Preparação para Deploy - Versão de Testes

## ❌ Status Atual: **NÃO PRONTO PARA DEPLOY**

## 🔴 Problemas Críticos Encontrados

### 1. URLs Hardcoded no Frontend
**Arquivos afetados:**
- `frontend/src/lib/api.ts` (linha 4)
- `frontend/src/pages/admin/ChatView.tsx` (linha 16)
- `frontend/src/pages/client/Chat.tsx` (linha 20)
- `frontend/src/contexts/ChatContext.tsx` (linha 57)

**Problema:** Todas as URLs da API estão hardcoded como `http://localhost:8080`

**Solução necessária:** 
- Criar variáveis de ambiente para a URL da API
- Usar `import.meta.env.VITE_API_URL` no Vite

---

### 2. Segurança - Credenciais Expostas
**Arquivo:** `backend/src/main/resources/application.properties`

**Problemas:**
- ✅ Senha do PostgreSQL exposta: `5desetembro`
- ✅ Secret JWT exposto: `palavra-secreta` (muito fraco)
- ✅ Chave da OpenAI exposta no código
- ✅ Credenciais hardcoded (não devem estar no repositório)

**Solução necessária:**
- Mover todas as credenciais para variáveis de ambiente
- Criar `.env` e adicionar ao `.gitignore`
- Usar secrets management no ambiente de produção
- Gerar secrets fortes para JWT

---

### 3. Configurações Inadequadas para Produção
**Arquivo:** `backend/src/main/resources/application.properties`

**Problemas:**
```properties
spring.jpa.show-sql=true                    # ⚠️ Não deve estar ativo em produção
spring.jpa.hibernate.ddl-auto=update        # ⚠️ Perigoso em produção (pode causar perda de dados)
server.error.include-message=always         # ⚠️ Expõe detalhes de erro
```

**Solução necessária:**
- Criar `application-prod.properties` com configurações seguras
- Desabilitar `show-sql` em produção
- Mudar `ddl-auto` para `validate` ou `none` em produção
- Limitar informações de erro em produção

---

### 4. CORS Muito Permissivo
**Arquivo:** `backend/src/main/java/com/advocacychat/backend/config/SecurityConfig.java`

**Problema:**
```java
configuration.addAllowedOriginPattern("*");  // ⚠️ Permite qualquer origem
```

**Solução necessária:**
- Configurar origens específicas para produção
- Usar variáveis de ambiente para URLs permitidas

---

### 5. Falta de Configuração de Ambiente
**Problemas:**
- ❌ Sem arquivos `.env.example`
- ❌ Sem `application-prod.properties`
- ❌ Sem configuração de variáveis de ambiente
- ❌ Backend não lê variáveis de ambiente

**Solução necessária:**
- Criar `.env.example` com template
- Criar `application-prod.properties`
- Configurar Spring Boot para usar variáveis de ambiente

---

### 6. Falta de Arquivos de Deploy
**Problemas:**
- ❌ Sem Dockerfile para backend
- ❌ Sem Dockerfile para frontend
- ❌ Sem docker-compose.yml
- ❌ Sem scripts de build/deploy
- ❌ Sem documentação de deploy

**Solução necessária:**
- Criar Dockerfiles para backend e frontend
- Criar docker-compose.yml para ambiente de testes
- Documentar processo de deploy

---

## ✅ O Que Está Funcionando

1. ✅ Build scripts configurados (Maven + Vite)
2. ✅ Estrutura de projeto organizada
3. ✅ Autenticação JWT implementada
4. ✅ WebSocket configurado
5. ✅ CORS configurado (mas muito permissivo)
6. ✅ Dependências atualizadas

---

## 📋 Checklist de Ações Necessárias

### Prioridade ALTA (Bloqueadores)
- [ ] Remover URLs hardcoded do frontend
- [ ] Mover todas as credenciais para variáveis de ambiente
- [ ] Criar arquivos `.env.example`
- [ ] Adicionar `.env` ao `.gitignore`
- [ ] Criar `application-prod.properties`
- [ ] Configurar CORS com origens específicas
- [ ] Desabilitar `show-sql` em produção
- [ ] Mudar `ddl-auto` para modo seguro em produção

### Prioridade MÉDIA (Importante)
- [ ] Criar Dockerfile para backend
- [ ] Criar Dockerfile para frontend
- [ ] Criar docker-compose.yml
- [ ] Configurar secrets management
- [ ] Gerar secret JWT forte
- [ ] Documentar processo de deploy
- [ ] Criar script de build/deploy

### Prioridade BAIXA (Melhorias)
- [ ] Configurar CI/CD
- [ ] Adicionar health checks
- [ ] Configurar logging estruturado
- [ ] Adicionar monitoramento
- [ ] Configurar backup do banco de dados

---

## 🔧 Exemplo de Estrutura Necessária

```
.
├── .env.example
├── docker-compose.yml
├── backend/
│   ├── Dockerfile
│   └── src/main/resources/
│       ├── application.properties (dev)
│       └── application-prod.properties (produção)
└── frontend/
    ├── Dockerfile
    ├── .env.example
    └── .env (não versionado)
```

---

## ⚠️ Avisos de Segurança

1. **NUNCA** commitar arquivos `.env` no repositório
2. **NUNCA** expor credenciais no código
3. **SEMPRE** usar secrets management em produção
4. **SEMPRE** usar HTTPS em produção
5. **SEMPRE** validar e sanitizar inputs
6. **SEMPRE** usar secrets fortes (mínimo 32 caracteres)

---

## 📝 Próximos Passos Recomendados

1. **Imediato:** Configurar variáveis de ambiente
2. **Curto prazo:** Criar arquivos de deploy (Docker)
3. **Médio prazo:** Configurar ambiente de staging
4. **Longo prazo:** Implementar CI/CD completo

---

**Última atualização:** $(date)
**Status:** ⛔ NÃO PRONTO PARA DEPLOY

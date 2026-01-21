# 🔧 Correções para Deploy - Problemas Encontrados e Resolvidos

## ✅ Problemas Corrigidos

### 1. **URLs Hardcoded para WebSocket** ⚠️ CRÍTICO
**Problema:** Três arquivos tinham URLs hardcoded `http://localhost:8080` que quebravam em produção:
- `frontend/src/pages/admin/ChatView.tsx`
- `frontend/src/pages/client/Chat.tsx`  
- `frontend/src/contexts/ChatContext.tsx`

**Solução:** 
- Criado arquivo centralizado `frontend/src/lib/config.ts` com função `getApiBaseURL()`
- Todas as URLs agora usam variável de ambiente `VITE_API_URL`
- Fallback inteligente para desenvolvimento e produção

### 2. **API Base URL** ⚠️ CRÍTICO
**Problema:** `api.ts` tinha fallback, mas não estava centralizado

**Solução:**
- `api.ts` agora usa a função centralizada `getApiBaseURL()`
- Consistência entre WebSocket e requisições HTTP

### 3. **Configuração de Variáveis de Ambiente**
**Problema:** Sem documentação de variáveis necessárias

**Solução:**
- Função `getApiBaseURL()` com fallbacks apropriados
- Warnings no console quando variáveis não estão configuradas
- Documentação abaixo sobre como configurar

---

## 📋 Configuração Necessária para Deploy

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto `frontend/` com:

```env
VITE_API_URL=https://seu-backend.com
```

**Importante:**
- Em **desenvolvimento**: Use `http://localhost:8080`
- Em **produção**: Use a URL completa do seu backend (ex: `https://api.seudominio.com`)
- A URL **NÃO** deve terminar com `/`
- Use `https://` em produção (nunca `http://`)

---

## 🔍 Outros Problemas Identificados (Já Corrigidos Anteriormente)

### ✅ Tratamento de Erros no Login
- Funções `getAdvogado()` e `getCliente()` agora não bloqueiam o login
- Erros são tratados silenciosamente, permitindo redirecionamento

### ✅ Interceptor de Erros Axios
- Tratamento automático de erros 401 (limpa token)
- Logs de debug apenas em desenvolvimento
- Mensagens de erro específicas por tipo

---

## 🚨 Problemas Potenciais Ainda Não Resolvidos

### 1. **Case-Sensitivity em Imports** ⚠️ ATENÇÃO
**Problema:** Windows é case-insensitive, mas Linux (servidores) é case-sensitive

**Arquivos com possível problema:**
- `frontend/src/types/Chat.tsx` (C maiúsculo)
- Imports usam `@/types/Chat` - pode quebrar em Linux

**Recomendação:** 
- Verificar se todos os imports estão com case correto
- Testar build em ambiente Linux antes do deploy

### 2. **CORS no Backend** ⚠️ VERIFICAR
**Problema:** CORS permite qualquer origem (`*`) em desenvolvimento

**Ação necessária:**
- Configurar CORS no backend para aceitar apenas domínios específicos em produção
- Verificar `backend/src/main/java/com/advocacychat/backend/config/SecurityConfig.java`

### 3. **Variáveis de Ambiente no Build**
**Importante:** No Vite, variáveis de ambiente são embutidas no build
- Se você definir `VITE_API_URL` em produção, ela será embutida no JavaScript
- Não é possível mudar após o build sem rebuild

---

## 📝 Checklist de Deploy

Antes do deploy, certifique-se de:

- [ ] Criar arquivo `.env` ou configurar `VITE_API_URL` no ambiente de build
- [ ] Configurar CORS no backend para aceitar o domínio do frontend
- [ ] Verificar que todas as URLs usam `https://` em produção
- [ ] Testar WebSocket connection em produção
- [ ] Verificar logs do console do navegador para warnings
- [ ] Testar login e autenticação em produção
- [ ] Verificar que todas as requisições HTTP funcionam

---

## 🔧 Arquivos Modificados

1. ✅ `frontend/src/lib/config.ts` (NOVO)
2. ✅ `frontend/src/lib/api.ts`
3. ✅ `frontend/src/pages/admin/ChatView.tsx`
4. ✅ `frontend/src/pages/client/Chat.tsx`
5. ✅ `frontend/src/contexts/ChatContext.tsx`
6. ✅ `frontend/src/contexts/AuthContext.tsx` (já corrigido anteriormente)
7. ✅ `frontend/src/pages/Login.tsx` (já corrigido anteriormente)

---

## 🎯 Próximos Passos Recomendados

1. **Imediato:** Configurar `VITE_API_URL` no ambiente de produção
2. **Curto prazo:** Testar WebSocket em produção
3. **Médio prazo:** Configurar CORS adequadamente
4. **Longo prazo:** Implementar health checks e monitoramento

---

**Data:** $(date)
**Status:** ✅ Principais problemas críticos corrigidos

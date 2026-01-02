import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import { ChatProvider } from "@/contexts/ChatContext";
import { ProtectedRoute } from "./components/ProtectedRoute";

// Public Pages
import LandingPage from "./pages/LandingPage";
import Checkout from "./pages/Checkout";
import PaymentPix from "./pages/PaymentPix";
import PaymentCreditCard from "./pages/PaymentCreditCard";
import PaymentSuccess from "./pages/PaymentSuccess";
import Login from "./pages/Login";

// Client Pages
import ClientChat from "./pages/client/Chat";
import ClientNovidades from "./pages/client/Novidades";
import ClientAgendamento from "./pages/client/Agendamento";
import ClientAssinatura from "./pages/client/Assinatura";
import ClientPerfil from "./pages/client/Perfil";

// Admin Pages
import AdminDashboard from "./pages/admin/Dashboard";
import AdminPosts from "./pages/admin/Posts";
import AdminChats from "./pages/admin/Chats";
import AdminChatView from "./pages/admin/ChatView";
import AdminAgendamentos from "./pages/admin/Agendamentos";
import AdminClientes from "./pages/admin/Clientes";

import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            {/* ================= PUBLIC ================= */}
            <Route path="/" element={<LandingPage />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/payment-pix" element={<PaymentPix />} />
            <Route path="/payment-credit-card" element={<PaymentCreditCard />} />
            <Route path="/payment-success" element={<PaymentSuccess />} />
            <Route path="/login" element={<Login />} />

            {/* ================= CLIENT ================= */}
            <Route
              path="/client/*"
              element={
                <ProtectedRoute requiredType="client">
                  <ChatProvider>
                    <Routes>
                      <Route path="chat" element={<ClientChat />} />
                      <Route path="novidades" element={<ClientNovidades />} />
                      <Route path="agendamento" element={<ClientAgendamento />} />
                      <Route path="assinatura" element={<ClientAssinatura />} />
                      <Route path="perfil" element={<ClientPerfil />} />
                    </Routes>
                  </ChatProvider>
                </ProtectedRoute>
              }
            />

            {/* ================= ADMIN ================= */}
            <Route
              path="/admin/dashboard"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminDashboard />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/posts"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminPosts />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/chats"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminChats />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/chat-view"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminChatView />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/agendamentos"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminAgendamentos />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/clientes"
              element={
                <ProtectedRoute requiredType="admin">
                  <AdminClientes />
                </ProtectedRoute>
              }
            />

            {/* ================= 404 ================= */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </AuthProvider>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;

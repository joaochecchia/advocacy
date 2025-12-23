import { Navigate } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";

interface ProtectedRouteProps {
  children: JSX.Element;
  requiredType?: "admin" | "client";
}

export const ProtectedRoute = ({
  children,
  requiredType,
}: ProtectedRouteProps) => {
  const { user, isAuthenticated, loading } = useAuth();

  // Aguardando restore da sessão
  if (loading) {
    return null; // ou spinner
  }

  // Não autenticado
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // Tipo de usuário inválido
  if (requiredType && user?.type !== requiredType) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

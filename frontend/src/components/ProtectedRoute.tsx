import { Navigate } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';

interface ProtectedRouteProps {
  children: React.ReactNode;
  requiredType?: 'client' | 'admin';
}

export const ProtectedRoute = ({ children, requiredType }: ProtectedRouteProps) => {
  const { isAuthenticated, user } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (requiredType && user?.type !== requiredType) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};

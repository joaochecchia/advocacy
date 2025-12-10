import React, { createContext, useContext, useState, ReactNode } from 'react';
import { useNavigate } from 'react-router-dom';

type UserType = 'client' | 'admin' | null;

interface User {
  id: number;
  name: string;
  email: string;
  type: UserType;
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const navigate = useNavigate();

  const login = async (email: string, password: string) => {
    // Simula chamada de API
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // Simula resposta do backend baseada no email
    const userType: UserType = email.includes('admin') ? 'admin' : 'client';
    
    const mockUser: User = {
      id: 1,
      name: userType === 'admin' ? 'Administrador' : 'Cliente',
      email,
      type: userType,
    };
    
    setUser(mockUser);
    
    // Redireciona baseado no tipo de usuário
    if (userType === 'admin') {
      navigate('/admin/dashboard');
    } else {
      navigate('/client/chat');
    }
  };

  const logout = () => {
    setUser(null);
    navigate('/login');
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

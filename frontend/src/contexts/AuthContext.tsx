import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import { Cliente } from "@/types/cliente"
import { Advogado } from "@/types/advogado"
import { ApiResponse } from "@/types/apiResponse"
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { api } from "@/lib/api";

import { getCliente } from "@/services/ClienteService";
import { getAdvogado } from "@/services/advogadoService";

type UserType = "admin" | "client";

/* =============================
   Tipos genéricos do Context
============================= */

interface User {
  id: number;
  email: string;
  nome: string; 
  type: UserType;
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  loading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

/* =======================
   Tipagem REAL do JWT
======================= */

interface JwtPayload {
  sub: string;
  usuarioId: number;
  nomeUsuario: string;
  tipoUsuario: "ADVOGADO" | "CLIENTE";
  exp: number;
  iat: number;
}

/* =======================
   Context
======================= */

const AuthContext = createContext<AuthContextType | undefined>(undefined);

/* =======================
   Provider
======================= */

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  /* =======================
     LOGIN
  ======================= */

  const login = async (email: string, password: string) => {
    const response = await api.post("/auth/login", {
      email,
      senha: password,
    });

    const { token, id } = response.data.Body;

    console.log("JWT recebido:", token);

    const decoded = jwtDecode<JwtPayload>(token);

    console.log("JWT decodificado:", decoded);

    const userType: UserType =
      decoded.tipoUsuario === "ADVOGADO" ? "admin" : "client";

    const userData: User = {
      id: decoded.usuarioId || id, 
      email: decoded.sub || email,
      nome: decoded.nomeUsuario || "",
      type: userType,
    };

    localStorage.setItem("token", token); 
    localStorage.setItem("userId", String(userData.id));
    localStorage.setItem("userType", userType);
    localStorage.setItem("email", userData.email);
    localStorage.setItem("userName", userData.nome); 

    setUser(userData);

    try {
      if (userType === "admin") {
        try {
          const advogado = await getAdvogado();
          console.log("Dados do advogado:", advogado);
        } catch (error) {
          console.warn("Erro ao buscar dados do advogado:", error);
        }
        navigate("/admin/dashboard");
      } else {
        try {
          const cliente = await getCliente();
          localStorage.setItem("Cliente", JSON.stringify(cliente));
          console.log("Dados do cliente:", cliente);
        } catch (error) {
          console.warn("Erro ao buscar dados do cliente:", error);
        }
        navigate("/client/chat");
      }
    } catch (error) {
      console.error("Erro durante redirecionamento:", error);
      if (userType === "admin") {
        navigate("/admin/dashboard");
      } else {
        navigate("/client/chat");
      }
    }
  };

  /* =======================
     LOGOUT
  ======================= */

  const logout = () => {
    localStorage.clear();
    setUser(null);
    navigate("/login");
  };

  /* =======================
     RESTORE SESSION (F5)
  ======================= */

  useEffect(() => {
    const token = localStorage.getItem("token");
    const userType = localStorage.getItem("userType") as UserType | null;
    const userId = localStorage.getItem("userId");
    const email = localStorage.getItem("email");
    const nome = localStorage.getItem("userName"); 

    if (token && userType && userId) {
      setUser({
        id: Number(userId),
        email: email || "",
        nome: nome || "", 
        type: userType,
      });
    }

    setLoading(false); 
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        loading,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

/* =======================
   Hook
======================= */

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth deve ser usado dentro de AuthProvider");
  }

  return context;
};
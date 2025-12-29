import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { api } from "@/lib/api";

/* =======================
   Tipos do FRONT
======================= */

export type UserType = "admin" | "client";

interface User {
  id: number;
  email: string;
  type: UserType;
}

type ApiResponse<T> = {
  Message: string
  Body: T
}

export interface Cliente {
  idUsuario: number
  nome: string
  email: string
  tipoUsuario: string
  ativo: boolean
  criadoEmUsuario: string | null
  atualizadoEmUsuario: string | null
  idCliente: number
  cpf: string
  telefone: string
  criadoEmCliente: string | null
  chatIds: number[]
}


export interface Advogado {
  idUsuario: number
  nome: string
  email: string
  tipoUsuario: string
  ativo: boolean
  criadoEmUsuario: string | null
  atualizadoEmUsuario: string | null
  idAdvogado: number
  oab: string
  especialidade: string
  criadoEmAdvogado: string | null
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
  sub: string; // email
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
      id,
      email,
      type: userType,
    };

    // Persistência
    localStorage.setItem("token", token); 
    localStorage.setItem("userId", String(id));
    localStorage.setItem("userType", userType);
    localStorage.setItem("email", email);

    setUser(userData);

    // Redirect
    if (userType === "admin") {
      const response = await api.get<ApiResponse<Advogado>>(
        `/advogado/getAdvogadoByUserId/${id}`
      );

      const advogado = response.data.Body;
      localStorage.setItem("Advogado", JSON.stringify(advogado));

      console.log("Dados do advogado:", advogado);
      
      navigate("/admin/dashboard");
    } else {
      const response = await api.get<ApiResponse<Cliente>>(
        `/cliente/getClienteByUserId/${id}`
      );

      const cliente = response.data.Body;
      localStorage.setItem("Cliente", JSON.stringify(cliente));

      console.log("Dados do cliente:", cliente);
      
      navigate("/client/chat");
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

    if (token && userType && userId) {
      setUser({
        id: Number(userId),
        email: email || "",
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

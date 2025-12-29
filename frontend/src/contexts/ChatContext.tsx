import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import { Cliente } from "@/types/cliente"
import { Advogado } from "@/types/advogado"
import { ApiResponse } from "@/types/apiResponse";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { api } from "@/lib/api";
import { getCliente } from "@/services/ClienteService";
import { getAdvogado } from "@/services/advogadoService";

/* =======================
   Tipos de Body da API
======================= */

/* ==========================
    Cliente em localStorage
========================== */

const cliente = await getCliente();


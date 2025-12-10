import { NavLink } from 'react-router-dom';
import { 
  MessageSquare, 
  Newspaper, 
  Calendar, 
  CreditCard, 
  User, 
  LayoutDashboard, 
  FileText, 
  MessageCircle, 
  CalendarCheck, 
  Users, 
  LogOut 
} from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { cn } from '@/lib/utils';

interface SidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

const clientMenuItems = [
  { icon: MessageSquare, label: 'Chat Jurídico', path: '/client/chat' },
  { icon: Newspaper, label: 'Novidades', path: '/client/novidades' },
  { icon: Calendar, label: 'Agendamento', path: '/client/agendamento' },
  { icon: CreditCard, label: 'Assinatura', path: '/client/assinatura' },
  { icon: User, label: 'Perfil', path: '/client/perfil' },
];

const adminMenuItems = [
  { icon: LayoutDashboard, label: 'Dashboard', path: '/admin/dashboard' },
  { icon: FileText, label: 'Posts', path: '/admin/posts' },
  { icon: MessageCircle, label: 'Chats', path: '/admin/chats' },
  { icon: CalendarCheck, label: 'Agendamentos', path: '/admin/agendamentos' },
  { icon: Users, label: 'Clientes', path: '/admin/clientes' },
];

export const Sidebar = ({ isOpen, onClose }: SidebarProps) => {
  const { user, logout } = useAuth();
  const menuItems = user?.type === 'admin' ? adminMenuItems : clientMenuItems;

  return (
    <>
      {/* Overlay */}
      {isOpen && (
        <div
          className="fixed inset-0 bg-black/50 z-40 lg:hidden"
          onClick={onClose}
        />
      )}

      {/* Sidebar */}
      <aside
        className={cn(
          "fixed top-0 left-0 h-full w-64 bg-sidebar transform transition-transform duration-300 z-50",
          "lg:translate-x-0",
          isOpen ? "translate-x-0" : "-translate-x-full"
        )}
      >
        <div className="flex flex-col h-full">
          {/* Logo */}
          <div className="p-6 border-b border-sidebar-border">
            <h2 className="text-xl font-bold text-sidebar-foreground">
              Advocacia SaaS
            </h2>
            <p className="text-sm text-sidebar-foreground/70 mt-1">
              {user?.type === 'admin' ? 'Painel Admin' : 'Portal do Cliente'}
            </p>
          </div>

          {/* Menu Items */}
          <nav className="flex-1 p-4 space-y-2 overflow-y-auto">
            {menuItems.map((item) => {
              const Icon = item.icon;
              return (
                <NavLink
                  key={item.path}
                  to={item.path}
                  onClick={onClose}
                  className={({ isActive }) =>
                    cn(
                      "flex items-center gap-3 px-4 py-3 rounded-lg transition-all",
                      isActive
                        ? "bg-sidebar-accent text-accent"
                        : "text-sidebar-foreground hover:bg-sidebar-accent/50"
                    )
                  }
                >
                  <Icon size={20} className="flex-shrink-0" />
                  <span className="truncate">{item.label}</span>
                </NavLink>
              );
            })}
          </nav>

          {/* User Info & Logout */}
          <div className="p-4 border-t border-sidebar-border">
            <div className="mb-3 px-4">
              <p className="text-sm font-medium text-sidebar-foreground">{user?.name}</p>
              <p className="text-xs text-sidebar-foreground/70">{user?.email}</p>
            </div>
            <button
              onClick={logout}
              className="w-full flex items-center gap-3 px-4 py-3 rounded-lg text-sidebar-foreground hover:bg-sidebar-accent/50 transition-all"
            >
              <LogOut size={20} />
              <span>Sair</span>
            </button>
          </div>
        </div>
      </aside>
    </>
  );
};

import { ReactNode, useState } from 'react';
import { Menu, X } from 'lucide-react';
import { Sidebar } from './Sidebar';

interface LayoutProps {
  children: ReactNode;
}

export const Layout = ({ children }: LayoutProps) => {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <div className="min-h-screen bg-secondary">
      {/* Mobile Header */}
      <div className="lg:hidden fixed top-0 left-0 right-0 h-14 bg-sidebar z-40 flex items-center px-4">
        <button
          onClick={() => setSidebarOpen(!sidebarOpen)}
          className="text-sidebar-foreground flex-shrink-0"
        >
          {sidebarOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
        <h1 className="ml-4 text-base font-semibold text-sidebar-foreground truncate">
          Advocacia SaaS
        </h1>
      </div>

      {/* Sidebar */}
      <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />

      {/* Main Content */}
      <div className="lg:ml-64 pt-14 lg:pt-0">
        <main className="p-4 sm:p-6">
          {children}
        </main>
      </div>
    </div>
  );
};

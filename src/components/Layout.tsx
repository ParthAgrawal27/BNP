import { Outlet } from 'react-router-dom';
import { Bell, Search, CircleHelp } from 'lucide-react';
import Sidebar from './Sidebar';
export default function Layout(){return <div className="shell"><Sidebar/><div className="workspace"><header className="topbar"><div className="breadcrumb">Workspace <span>/</span> Treasury Overview</div><div className="header-actions"><span className="header-search"><Search size={15}/> Search workspace</span><CircleHelp size={18}/><Bell size={18}/><div className="avatar small">TP</div></div></header><main className="content"><Outlet/></main></div></div>;}
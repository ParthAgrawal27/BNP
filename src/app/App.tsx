import { Navigate, Route, Routes } from 'react-router-dom';
import Layout from '../components/Layout';
import DashboardPage from '../features/dashboard/DashboardPage';
import PlaceholderPage from '../features/shared/PlaceholderPage';
export default function App(){return <Routes><Route element={<Layout/>}><Route index element={<DashboardPage/>}/>{['transactions','forecast','simulator','alerts','advisor','reports','admin'].map(path=><Route key={path} path={path} element={<PlaceholderPage/>}/>)}</Route><Route path="*" element={<Navigate to="/" replace/>}/></Routes>;}
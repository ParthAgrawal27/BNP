import type { DashboardFilters, DashboardSummary } from '../types/treasury';
import { mockDashboard } from '../mocks/dashboard';
const BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8000/api';
const USE_MOCKS = import.meta.env.VITE_USE_MOCKS !== 'false';
export async function getDashboardSummary(filters:DashboardFilters): Promise<DashboardSummary> {
 if(USE_MOCKS) return mockDashboard;
 const query = new URLSearchParams({region:filters.region,currency:filters.currency,entity:filters.entity});
 const response = await fetch(`${BASE}/dashboard/summary?${query}`,{credentials:'include'});
 if(!response.ok) throw new Error(`Dashboard request failed: ${response.status}`);
 return response.json() as Promise<DashboardSummary>;
}
// Ownership: each backend module defines and implements its endpoint; see docs/API_CONTRACTS.md.

export type Region = 'All Regions' | 'APAC' | 'EMEA' | 'NAMER';
export type Currency = 'All Currencies' | 'USD' | 'EUR' | 'JPY' | 'GBP';
export interface DashboardFilters { region: Region; currency: Currency; entity: string; }
export interface Metric { label:string; value:string; change:string; }
export interface ForecastPoint { day:string; baseline:number; low:number; high:number; threshold:number; }
export interface Alert { id:string; entity:string; currency:string; status:'Warning'|'Critical'|'Emergency'; detail:string; }
export interface DashboardSummary { metrics:Metric[]; forecast:ForecastPoint[]; alerts:Alert[]; updatedAt:string; }

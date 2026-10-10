import type { DashboardSummary } from '../types/treasury';
// DESIGN-ONLY MOCK VALUES. These numbers are not computed from the provided dataset.
export const mockDashboard: DashboardSummary = {
 updatedAt:'Demo data · not live',
 metrics:[
  {label:'Global Cash Position',value:'$24.8M',change:'Illustrative balance'},
  {label:'Net Cash Flow',value:'+$1.32M',change:'Illustrative daily change'},
  {label:'Available Surplus',value:'$3.4M',change:'Illustrative opportunity'},
  {label:'Active Alerts',value:'3',change:'Illustrative alerts'},
 ],
 forecast:Array.from({length:30},(_,i)=>({day:`D${i+1}`,baseline:18.2+i*.12+Math.sin(i/2)*1.7,low:16.2+i*.11+Math.sin(i/2)*1.7,high:20.2+i*.13+Math.sin(i/2)*1.7,threshold:17})),
 alerts:[
  {id:'AL-001',entity:'Sample EMEA entity',currency:'EUR',status:'Critical',detail:'Projected balance near buffer'},
  {id:'AL-002',entity:'Sample APAC entity',currency:'JPY',status:'Warning',detail:'Upcoming scheduled outflows'},
  {id:'AL-003',entity:'Sample NAMER entity',currency:'USD',status:'Emergency',detail:'Projected balance below configured buffer'},
 ],
};
import { Link, useLocation } from 'react-router-dom';
import { Blocks, ArrowLeft, Wrench } from 'lucide-react';
const metadata:Record<string,{title:string,owner:string,details:string[]}>={
 '/transactions':{title:'Transactions & Upload',owner:'Member 1 + Member 3',details:['Transaction classification and table','CSV/Excel upload','Entity, currency, date filters']},
 '/forecast':{title:'30-Day Forecast',owner:'Member 2 + Member 3',details:['Forecast by entity/currency','Confidence intervals','Model evaluation details']},
 '/simulator':{title:'What-If Simulator',owner:'Member 4 + Member 3',details:['Payment delays, capex and FX shocks','Interest-rate and risk assumptions','Baseline vs scenario comparison']},
 '/alerts':{title:'Alert Matrix',owner:'Member 6 + Member 3',details:['Warning / Critical / Emergency','Alert history and resolution','Threshold configuration']},
 '/advisor':{title:'AI Treasury Advisor',owner:'Member 5 + Member 3',details:['Data-grounded answers','Forecast and alert explanations','Prioritized recommended actions']},
 '/reports':{title:'Scheduled Reports',owner:'Member 6 + Member 3',details:['Start- and end-of-day reports','PDF/CSV export','Schedule and recipients']},
 '/admin':{title:'Admin & Access Control',owner:'Member 6 + Member 3',details:['Role-based access','Entity visibility','Threshold and scheduler settings']},
};
export default function PlaceholderPage(){const {pathname}=useLocation();const item=metadata[pathname]??metadata['/admin'];return <><div className="page-head"><div><div className="eyebrow"><Wrench size={13}/> FEATURE WORKSPACE</div><h1>{item.title}</h1><p>Module owner: {item.owner}</p></div></div><div className="panel placeholder"><Blocks size={40}/><h2>Ready for module integration</h2><p>This page is intentionally a skeleton. Build UI components in this feature folder, then connect to the agreed backend API.</p><div className="placeholder-tasks">{item.details.map(task=><div key={task}><span>✓</span>{task}</div>)}</div><Link className="btn primary" to="/"><ArrowLeft size={15}/> Back to Overview</Link></div></>; }
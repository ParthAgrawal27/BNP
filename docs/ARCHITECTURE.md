# Architecture decisions

```text
                   React + Vite UI
                         |
             Router / Shared App Layout
                         |
       Dashboard | Forecast | Simulator | Alerts
       Advisor   | Reports  | Transactions | Admin
                         |
               src/services/api.ts
                         |
                    REST Backend
                         |
       Ingestion | Forecast | Scenario | Alert
       Advisory  | Auth     | Report
                         |
           Database / Data Processing
```

### Boundary rules
- Backend is the source of truth for balances, forecasts, thresholds, AI grounding, scenarios and permissions.
- Frontend only formats numbers and renders financial outputs (e.g. no duplicate financial formulas).
- Cash forecasts require baseline/currency/entity/date plus confidence intervals; simulations must never mutate the baseline.
- For mixed-currency totals, backend must specify a common reporting currency and FX valuation date.
- Data source contains historical transactions; do not assume future cash flows are guaranteed.
- Guardrails from supplied PDF: synthetic data, functional demo focus, no internet-required external services.

### UI navigation
Overview → Transactions → Forecasting → What-If Simulator → Alert Matrix → AI Advisor → Reports → Admin.

### Dashboard panels
KPI cards, region/entity/currency selectors, 30-day chart, currency exposure, alerts, module shortcuts. Additional PDF-required charts can be added via reusable chart components after schema agreement.

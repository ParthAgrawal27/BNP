# Proposed API contracts (team must confirm before implementation)

These are **suggested** paths, not existing backend routes.

| Endpoint | Owner | Purpose |
|---|---|---|
| `POST /api/transactions/upload` | 1 | Upload synthetic CSV, validation feedback |
| `GET /api/transactions` | 1 | Paginated transaction table |
| `GET /api/dashboard/summary` | 1 | Dashboard KPIs/cash positions; see TS type |
| `GET /api/forecasts` | 2 | 30-day entity/currency forecast with uncertainty |
| `POST /api/scenarios/simulate` | 4 | Scenario inputs, baseline vs scenario results |
| `POST /api/chat` | 5 | Grounded answer, citations, optional chart payload |
| `GET /api/alerts` | 6 | Current and historical alerts |
| `GET /api/reports` | 6 | SOD/EOD reports and schedules |
| `POST /api/auth/login` | 6 | Demo session role and entity scope |

## Example GET /api/dashboard/summary

Query params: `region`, `currency`, `entity`.

```json
{
  "updatedAt": "2026-10-10T09:00:00Z",
  "metrics": [{"label":"Global Cash Position","value":"$24.8M","change":"Demo"}],
  "forecast": [{"day":"D1","baseline":18.2,"low":16.2,"high":20.2,"threshold":17.0}],
  "alerts": [{"id":"AL-1","entity":"Example Entity","currency":"EUR","status":"Warning","detail":"Near threshold"}]
}
```

Values above are API illustration only, **not real calculations**. For production-quality contracts, prefer numeric values with explicit currency/unit, ISO dates, valuation time, and immutable entity IDs. This skeleton uses simplified strings for visual convenience.

## Critical contract questions

1. Are cash balances per `entity+currency`, or per `entity+account+currency`?
2. Does forecast output represent closing balances or incremental daily movements?
3. Which FX snapshot drives reporting-currency totals?
4. How do cancelled/failed/pending transfers affect daily positions?
5. How are region/entity permissions returned and enforced?
6. How are scenario changes represented (e.g. payment IDs, delay days, rate basis points)?

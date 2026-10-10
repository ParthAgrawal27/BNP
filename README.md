# Treasury Pulse â€” Shared Frontend Starter

> **Team onboarding and contribution guide** | React 18 + TypeScript + Vite 6
>
> **Status:** Shared **frontend skeleton**, not the finished Treasury Pulse system. Everyone should clone the **same GitHub repository** and contribute on separate feature branches.

## Contents

1. [What this repository is](#1-what-this-repository-is)
2. [What is already working](#2-what-is-already-working)
3. [Prerequisites: set up a fresh computer](#3-prerequisites-set-up-a-fresh-computer)
4. [Download and run from zero](#4-download-and-run-from-zero)
5. [Environment configuration and mock mode](#5-environment-configuration-and-mock-mode)
6. [Architecture and project structure](#6-architecture-and-project-structure)
7. [How to build a new feature page](#7-how-to-build-a-new-feature-page)
8. [How to connect backend APIs](#8-how-to-connect-backend-apis)
9. [Responsibilities for all six members](#9-responsibilities-for-all-six-members)
10. [GitHub collaboration workflow](#10-github-collaboration-workflow)
11. [Required checks before a pull request](#11-required-checks-before-a-pull-request)
12. [Common setup problems and solutions](#12-common-setup-problems-and-solutions)
13. [Milestones and integration order](#13-milestones-and-integration-order)
14. [Project-specific financial and security rules](#14-project-specific-financial-and-security-rules)
15. [Definition of done and handoff checklist](#15-definition-of-done-and-handoff-checklist)

---

## 1. What this repository is

**Treasury Pulse: Short-term Liquidity Manager** is a centralized treasury dashboard for viewing cash positions, 30-day forecasts, liquidity gaps, alerts, scenarios, reports and AI-assisted analysis across entities and currencies.

The frontend is intentionally shared across the six-person team. It supplies one common layout, navigation and a typed API boundary so backend developers can work independently without making incompatible screens.

**Source documents:** the provided *2026 Use Case Treasury Pulse* PDF and sample transactions ZIP. The use case calls for ingestion, daily cash positions, 30-day forecasting with uncertainty, alert matrix, what-if analysis, SOD/EOD reporting, roles, and optional RAG advisory. This repository does **not** implement those backend functions yet.

### Intended development flow

```text
User / Browser
    |
    v
React + TypeScript (Vite)
    |
    +-- Shared Layout / Sidebar / Router
    +-- Dashboard / Transactions / Forecast / Simulator
    +-- Alerts / Advisor / Reports / Admin
    |
    v
src/services/api.ts  (typed HTTP boundary)
    |
    v
Backend REST APIs (team members 1, 2, 4, 5, 6)
    |
    v
DB + Cash calculations + Forecasts + Scenarios + Alerts + AI
```

The backendâ€”not Reactâ€”must be the source of truth for financial calculations, user permissions, forecasting and AI grounding.

## 2. What is already working

| Area | Present in starter? | Notes |
|---|---|---|
| Vite/React/TypeScript app | Yes | Run with `npm run dev` |
| Shared sidebar, header and routing | Yes | `src/components/` and `src/app/App.tsx` |
| Overview dashboard UI | Yes | Illustrative mock KPIs, forecast and alerts |
| Dashboard API boundary | Yes | `getDashboardSummary()` in `src/services/api.ts` |
| Environment switching | Yes | `VITE_USE_MOCKS` controls dashboard data source |
| Transactions, Forecasting, Simulator, Alerts, Advisor, Reports, Admin pages | Placeholders | All currently use `PlaceholderPage` |
| Authentication and role protection | No | Backend + frontend integration required |
| Functional file upload, simulation, reports and chatbot | No | Backend integration required |
| Automated tests | No | Add tests when implementing features |

**Do not mistake demo UI for real output.** Dashboard currency distribution, cash totals, alerts and forecast are hard-coded illustrations. Demo filters do not filter real transactions. The Export Report button is a stub. Some shortcuts have only a destination page.

## 3. Prerequisites: set up a fresh computer

### Software to install

1. **Node.js LTS** (recommended: Node.js 22 LTS for this starter) with npm from <https://nodejs.org/>.
2. **Git** from <https://git-scm.com/downloads>.
3. **VS Code** (recommended editor) from <https://code.visualstudio.com/>.
4. A **GitHub account** and access to the team's repository.
5. A modern browser (Chrome, Edge or Firefox).

After installation, open **VS Code > Terminal > New Terminal** or Windows PowerShell and check:

```powershell
node --version
npm --version
git --version
```

These commands should print version numbers. If `node` or `git` is not recognized, restart the terminal after installation and confirm the program was added to PATH.

**Recommended VS Code extensions:** ESLint, Prettier, GitLens. Extensions are optional; this starter does not yet configure ESLint/Prettier scripts.

### Git first-time identity setup (once per computer)

```bash
git config --global user.name "Your Name"
git config --global user.email "your-github-email@example.com"
```

## 4. Download and run from zero

### Path A â€” team GitHub repository (recommended)

**Repository owner, once:** Create an empty GitHub repository such as `treasury-pulse-frontend`. Extract the supplied starter ZIP on your computer. Open its `treasury-pulse-frontend-starter` folder, then execute:

```bash
git init
git add .
git commit -m "chore: initialize shared frontend skeleton"
git branch -M main
git remote add origin https://github.com/TEAM-ACCOUNT/treasury-pulse-frontend.git
git push -u origin main
```

Replace `TEAM-ACCOUNT` with the actual account or organization. GitHub may require browser authentication or a token; do not commit tokens or passwords.

**Every other member:** Clone the team's repository; do **not** independently initialize another Git repository:

```bash
git clone https://github.com/TEAM-ACCOUNT/treasury-pulse-frontend.git
cd treasury-pulse-frontend
npm install
```

### Path B â€” use the downloaded ZIP without GitHub yet

1. Extract `Treasury_Pulse_Frontend_Skeleton.zip`.
2. Open the extracted `treasury-pulse-frontend-starter` folder in VS Code.
3. Open a terminal in that folder (where `package.json` exists).
4. Run `npm install`.

### Create local environment file

Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

macOS/Linux/Git Bash:

```bash
cp .env.example .env
```

### Start the frontend

```bash
npm run dev
```

Vite prints a local address (normally **http://localhost:5173/**). Open it in your browser. You should see the Treasury Pulse sidebar and Overview dashboard. Click all navigation links to verify the placeholders load.

To stop the development server press **Ctrl+C** in the terminal.

### Build check

```bash
npm run build
npm run preview
```

`npm run build` runs TypeScript checks and builds the frontend into `dist/`. `npm run preview` serves the built files locally. The preview port may differ from the dev port. **There is no `npm test` or `npm run lint` script in the current starter.**

**Dependency consistency:** The starter ZIP has no lockfile. The repository owner should run `npm install` once and **commit the generated `package-lock.json`**. After that, team members should prefer `npm ci` for reproducible installs. Never commit `node_modules/` or `dist/`.

## 5. Environment configuration and mock mode

The supplied `.env.example` contains:

```env
VITE_API_BASE_URL=http://localhost:8000/api
VITE_USE_MOCKS=true
```

| Setting | Purpose |
|---|---|
| `VITE_USE_MOCKS=true` | Use illustrative mock dashboard; backend is not required |
| `VITE_USE_MOCKS=false` | Fetch dashboard data from REST backend |
| `VITE_API_BASE_URL` | Backend API prefix, currently `http://localhost:8000/api` |

**To connect a real backend:** Change `.env` to `VITE_USE_MOCKS=false`, start the backend, and ensure it implements the expected `GET /api/dashboard/summary` contract. Restart Vite after editing `.env`.

> **Important:** Currently the mock toggle only affects `getDashboardSummary()`; new feature services must implement their own mock/live behavior if required. Do not put any API secrets or database passwords in variables prefixed with `VITE_`: Vite exposes these values to the browser.

The starter's `fetch` uses `credentials: 'include'`. If you use cross-origin cookie sessions, the backend must configure CORS for the exact frontend origin and allow credentials; the browser must also accept the cookie settings. Use a consistent authentication design chosen by Member 6.

## 6. Architecture and project structure

```text
treasury-pulse-frontend-starter/
â”œâ”€â”€ docs/
â”‚   â”œâ”€â”€ ARCHITECTURE.md           # Design boundaries and data flow
â”‚   â””â”€â”€ API_CONTRACTS.md          # Proposed endpoints and schema questions
â”œâ”€â”€ src/
â”‚   â”œâ”€â”€ main.tsx                  # React entry point
â”‚   â”œâ”€â”€ styles.css                # Shared styling (owned by UI lead)
â”‚   â”œâ”€â”€ app/App.tsx               # Routes and shared layout
â”‚   â”œâ”€â”€ components/
â”‚   â”‚   â”œâ”€â”€ Layout.tsx            # App shell / top bar
â”‚   â”‚   â”œâ”€â”€ Sidebar.tsx           # Navigation
â”‚   â”‚   â”œâ”€â”€ MetricCard.tsx        # Reusable metric UI
â”‚   â”‚   â””â”€â”€ ForecastChart.tsx     # Recharts forecast visualization
â”‚   â”œâ”€â”€ features/
â”‚   â”‚   â”œâ”€â”€ dashboard/DashboardPage.tsx
â”‚   â”‚   â””â”€â”€ shared/PlaceholderPage.tsx
â”‚   â”œâ”€â”€ services/api.ts           # Existing dashboard service
â”‚   â”œâ”€â”€ types/treasury.ts         # Shared TypeScript contracts
â”‚   â””â”€â”€ mocks/dashboard.ts        # Demo dashboard response
â”œâ”€â”€ .env.example                  # Committable configuration template
â”œâ”€â”€ .gitignore
â”œâ”€â”€ package.json
â”œâ”€â”€ tsconfig.json
â”œâ”€â”€ vite.config.ts
â””â”€â”€ README.md
```

**Folder ownership rules**

- `src/app/`, `src/components/`, `src/styles.css`: **Member 3** owns shared UI and routing changes.
- `src/features/<module>/`: Put page-specific components and state here; create folders as needed.
- `src/services/`: Keep backend-facing API code here, not mixed into chart components.
- `src/types/`: TypeScript interfaces for API requests/responses and shared domain data.
- `src/mocks/`: Development-only sample API responses. Identify mock data clearly.
- `docs/API_CONTRACTS.md`: Shared agreement, updated **before** backend/frontend integration.

Read `docs/ARCHITECTURE.md` and `docs/API_CONTRACTS.md` before changing cross-module interfaces.

## 7. How to build a new feature page

**Example: Member 4 adds What-If Simulator UI.**

1. Confirm with Member 3 that you will contribute the feature page directly. Member 3 remains responsible for layout consistency.
2. Create `src/features/simulations/SimulatorPage.tsx`:

```tsx
export default function SimulatorPage() {
  return (
    <section className="panel">
      <h1>What-If Simulator</h1>
      <p>Scenario controls and baseline comparison will appear here.</p>
    </section>
  );
}
```

3. Ask Member 3 to replace the **existing** placeholder route in `src/app/App.tsx`. The current router maps `/simulator` (and other routes) to `PlaceholderPage`:

```tsx
import SimulatorPage from '../features/simulations/SimulatorPage';

// Within the existing <Route element={<Layout />}> group:
<Route path="simulator" element={<SimulatorPage />} />
```

Remove `simulator` from the placeholder route array, otherwise there will be competing routes. Do **not** create a second router or a second sidebar.

4. Implement the actual UI, e.g. `ScenarioControls`, `BaselineComparisonChart` and an accessible loading/error state.
5. Create/reuse a typed service (see next section). Use mock scenarios first; then connect the backend API.
6. Check desktop + mobile, error handling and `npm run build` before a pull request.

Current navigation paths: `/`, `/transactions`, `/forecast`, `/simulator`, `/alerts`, `/advisor`, `/reports`, `/admin`. Do not rename shared routes without team agreement.

## 8. How to connect backend APIs

### Step 1 â€” Agree on data contracts **before coding**

See `docs/API_CONTRACTS.md`. Those endpoints are **proposals**, not promises that services already exist.

| Proposed route | Responsible member | UI use |
|---|---|---|
| `POST /api/transactions/upload` | 1 | CSV upload and validation |
| `GET /api/transactions` | 1 | Transaction table |
| `GET /api/dashboard/summary` | 1 | Dashboard KPIs |
| `GET /api/forecasts` | 2 | Cash forecast |
| `POST /api/scenarios/simulate` | 4 | What-if outputs |
| `POST /api/chat` | 5 | AI explanations and action plans |
| `GET /api/alerts` | 6 | Liquidity warnings and history |
| `GET /api/reports` | 6 | SOD/EOD report UI |
| `POST /api/auth/login` | 6 | Demo user session |

Agree on request body, response shape, field names/types, pagination, error codes, permissions and sample JSON. **Frontend and backend should share fixtures or an OpenAPI specification.**

### Step 2 â€” Define TypeScript response types

Example only, not an already implemented service:

```ts
export interface ForecastPointResponse {
  date: string;                 // ISO date: YYYY-MM-DD
  entityId: string;
  currency: string;
  projectedBalance: number;     // agreed currency unit
  confidenceLow: number;
  confidenceHigh: number;
  threshold: number;
}
```

### Step 3 â€” Create a service function

For instance `src/services/forecastApi.ts`:

```ts
import type { ForecastPointResponse } from '../types/forecast';
const API = import.meta.env.VITE_API_BASE_URL;

export async function getForecast(entityId: string): Promise<ForecastPointResponse[]> {
  const params = new URLSearchParams({ entity: entityId });
  const response = await fetch(`${API}/forecasts?${params}`, {
    credentials: 'include',
  });
  if (!response.ok) throw new Error(`Forecast request failed (${response.status})`);
  return response.json() as Promise<ForecastPointResponse[]>;
}
```

Create `src/types/forecast.ts` with the interface above before using the sample code. Adapt the sample after agreeing on the actual endpoint schema.

### Step 4 â€” Connect in the feature component

Display **loading, empty, error and success** states. Do not silently swallow request failures. Keep API calls out of chart components. For complex data fetching, the team can adopt TanStack Query, but note that **it is not installed in the current starter**.

### Step 5 â€” Verify contract and real data

Start backend and frontend independently. In your browser's Developer Tools > Network, inspect URL, method, status, request payload and response. Confirm that filters affect backend responses, that user permissions are enforced server-side, and that financial figures come from calculated outputs rather than mocks.

## 9. Responsibilities for all six members

| Member | Primary work | Frontend handoff |
|---|---|---|
| **1 â€” Data + backend** | Ingestion, cleaning, balances, transaction APIs | Upload/table schemas, dashboard summary response and fixtures |
| **2 â€” Forecasting** | Forecast model, intervals, diagnostics | 30-day data structure, chart sample and accuracy metrics |
| **3 â€” Frontend lead** | Layout, shared components, routing, dashboard, integration | Review UI contributions and maintain consistent design |
| **4 â€” Simulation** | Timing, capex, FX, rates and counterparty scenarios | Scenario request schema and baseline-vs-scenario results |
| **5 â€” AI Advisor** | RAG/chat, explanations, recommendations | Chat response schema, supporting evidence and optional charts |
| **6 â€” Alerts/security/reporting** | Thresholds, alert history, roles, reports | Role/entity scopes, alert states, report and auth schemas |

Each backend member must supply: **API path + example request + example response + error cases + sample test data + how to run their service.** Member 3 should not need to reverse-engineer other people's responses.

## 10. GitHub collaboration workflow

### A. Rules everyone follows

- `main` stays **stable and buildable**; avoid pushing directly to it. Use protected branches and peer-reviewed PRs where available.
- One branch per task: `feat/m1-transactions`, `feat/m2-forecast`, `feat/m3-dashboard`, `feat/m4-simulator`, `feat/m5-advisor`, `feat/m6-alerts-reports`.
- Do not overwrite another member's work by copying entire folders into the repository.
- Make focused commits with meaningful messages: `feat(simulator): add payment delay slider`.
- Never commit `.env`, passwords, tokens, private data, `node_modules/`, or `dist/`.
- Discuss changes to `App.tsx`, `Sidebar.tsx`, `styles.css` and shared types with Member 3 first.

### B. Start work on a feature branch

```bash
git switch main
git pull origin main
git switch -c feat/m4-simulator
```

Change files, then:

```bash
git status
git add .
git commit -m "feat(simulator): add scenario page shell"
git push -u origin feat/m4-simulator
```

Open GitHub > **Pull requests** > **New pull request**. Compare `feat/m4-simulator` into `main`. Add screenshots, testing steps, API changes and known limitations. Ask for review. **Do not merge if the build is failing.**

### C. Keep a branch up to date

```bash
git fetch origin
git switch feat/m4-simulator
git merge origin/main
```

If there is a conflict, open each conflicted file and intentionally combine both changes. Do not automatically discard the other member's additions. Run `npm run build`, commit the merge if Git asks, then push.

### D. After PR is merged

```bash
git switch main
git pull origin main
```

Create a **new** feature branch for the next task.

### Team issue board

Create GitHub issues with owner, acceptance criteria, dependencies and relevant API contract. Use labels such as `frontend`, `backend`, `forecast`, `simulation`, `integration`, `bug` and `docs`. Record integration blockers rather than leaving them only in group chat.

## 11. Required checks before a pull request

- [ ] `npm run build` passes (TypeScript + Vite).
- [ ] Application starts using `npm run dev`.
- [ ] Existing sidebar links and dashboard still work.
- [ ] New feature handles loading, error, empty and success states.
- [ ] Changes are contained mainly within the owned feature directory.
- [ ] All API schemas changed in code are documented in `docs/API_CONTRACTS.md`.
- [ ] No real secrets, private/production financial records or accidental binary dependencies committed.
- [ ] Screenshot or short demo plus manual test instructions attached.
- [ ] Mock data is not presented as actual calculations.
- [ ] One other team member reviews the PR before merge.

This is currently a manual checklist because the starter does not yet include a dedicated test suite, linter or CI workflow. Add these later as a team improvement.

## 12. Common setup problems and solutions

| Problem | What to do |
|---|---|
| `npm` or `node` not recognized | Install Node.js LTS; restart PowerShell/VS Code; check PATH |
| PowerShell blocks `npm.ps1` | Use `npm.cmd install` / `npm.cmd run dev` or run the commands in Command Prompt; do not blindly weaken system security settings |
| `ENOENT` / cannot find `package.json` | `cd` into the extracted project folder and check `dir` / `ls` |
| `npm install` fails | Check internet, npm error output and compatible Node version; retry after fixing cause |
| `5173` already in use | Stop previous Vite instance or use the alternative local URL Vite prints |
| Backend requests fail | Verify backend is running, `VITE_USE_MOCKS=false`, `.env` API URL and correct route |
| CORS / cookie errors | Backend owner must configure allowed frontend origin and session credentials |
| Page shows placeholder | That module is not yet implemented; it is expected |
| Changing `.env` has no effect | Stop and restart `npm run dev` |
| `git push` rejected | `git pull`/`git fetch`, inspect permissions and branch protection; use PR workflow |
| Merge conflicts in router/style | Ask Member 3 to coordinate; keep both valid feature changes |

## 13. Milestones and integration order

**Phase 1 â€” Shared foundation:** Everyone clones and runs app; Member 3 validates common navigation, design conventions and branch workflow. Commit `package-lock.json`.

**Phase 2 â€” Data contracts:** Members 1, 2, 4, 5 and 6 supply documented JSON schemas and mock fixtures. Member 3 prepares route components. Agree on entity/currency/date filters and common error format.

**Phase 3 â€” Core integration:** Connect transactions, cash positions, 30-day forecast and alerts. Confirm filtering, balances and chart units end-to-end.

**Phase 4 â€” Advanced integration:** Connect scenarios, AI advisor, user access control, scheduled reports and additional dashboard views.

**Phase 5 â€” Quality and demo:** Validate edge cases, role isolation, CSV processing performance, forecast error metrics, response times, architecture/schema diagrams and the PDF's maximum two-minute demo requirement.

Integration order is a recommendation, not an imposed calendar deadline. Prioritize a working core over polishing unfinished advanced screens.

## 14. Project-specific financial and security rules

These rules come from the provided Treasury Pulse use case plus implementation safeguards:

1. **Liquidity gap:** `G = P - T`, where `P = previous balance + inflows - outflows`, and `T` is the configured threshold. Calculate on the backend with a defined period/entity/currency.
2. **Forecast horizon:** 30 days, per entity/currency, with confidence intervals. Label forecasts distinctly from historical actuals.
3. **Thresholds:** PDF gives examples EUR 50k, USD 60k, JPY 5M and GBP 40k. The document also specifies configurable Warning/Critical/Emergency zones; Member 6 should confirm precise boundary behavior in tests.
4. **Scenario changes:** Payment delays, capex postponement, FX shifts, loan/deposit rate shocks and counterparty risk changes are simulated without mutating stored baseline transactions.
5. **Multi-currency:** Never directly sum `USD + EUR + JPY`. Convert using a declared reporting currency, FX source/rate and valuation time.
6. **Transaction status:** Define treatment of settled, pending and failed payments with Member 1; avoid double-counting transfer legs.
7. **AI advisory:** Ground answers in retrieved system data; deterministic backend services calculate amounts. Show evidence, uncertainties and assumptions.
8. **Access control:** UI visibility is not security. Backend validates each request against the user's role and permitted entities/regions.
9. **Use-case guardrails:** Use synthetic/anonymized data, avoid production-grade security overhead and internet-dependent external services, and focus on a functional demo.
10. **Sample data:** Follow the source CSV schema. Do not assume fields referenced in the PDF automatically exist in the CSV; resolve any differences and record agreed derivations or synthetic assumptions.

## 15. Definition of done and handoff checklist

A module is ready for integration when its owner provides:

- [ ] Feature UI or a usable documented frontend contract
- [ ] Request/response schemas and 2â€“3 sample JSON cases
- [ ] Running backend endpoint where applicable
- [ ] Loading, empty, failure and permission-denied behavior
- [ ] Happy-path and edge-case test evidence
- [ ] Screenshot or quick walkthrough
- [ ] No breaking changes to shared UI/routes
- [ ] Successful frontend build and reviewed pull request

### One-time team kickoff checklist

- [ ] Repository created; all six members have access
- [ ] `main` contains the starter and `package-lock.json`
- [ ] Everyone can run `npm ci` and `npm run dev`
- [ ] Member 3 assigned as shared frontend code owner
- [ ] Branch names and PR review process agreed
- [ ] Backend language/host ports and auth approach agreed
- [ ] JSON API schemas reviewed in `docs/API_CONTRACTS.md`
- [ ] Dataset schema and FX assumptions recorded
- [ ] First end-to-end integration date agreed

---

## Reference files

- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) â€” application data flow and design boundaries.
- [`docs/API_CONTRACTS.md`](docs/API_CONTRACTS.md) â€” proposed endpoints and unresolved data contract questions.
- `src/mocks/dashboard.ts` â€” illustrative responses for developing without the backend.
- `src/types/treasury.ts` â€” existing frontend dashboard types (simplified for the skeleton).

**Start here:** Install Node and Git â†’ clone the repo â†’ `npm ci` (after lockfile is committed) â†’ copy `.env.example` â†’ `npm run dev` â†’ create your feature branch â†’ read API contracts â†’ implement in your owned folder â†’ `npm run build` â†’ open a PR.


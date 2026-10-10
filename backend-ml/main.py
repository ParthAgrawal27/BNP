from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Dict, Any, Optional
from models.forecaster import LiquidityForecaster
from models.rag_engine import TreasuryAdvisoryEngine
from data_processing import apply_what_if_scenario

app = FastAPI(title="Treasury Pulse ML Service")

# --- CORS Configuration added here ---
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000", "*"], # Allows Next.js to fetch data
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

forecaster = LiquidityForecaster()
advisory_engine = TreasuryAdvisoryEngine()

class PositionData(BaseModel):
    date: str
    amount: float

class ForecastRequest(BaseModel):
    entity: str
    currency: str
    historical_data: List[PositionData]

class WhatIfRequest(BaseModel):
    transactions: List[Dict[str, Any]]
    payment_delay_days: Optional[int] = 0
    fx_rate_shift_percent: Optional[float] = 0.0
    postpone_capex_days: Optional[int] = 0

class AdvisoryRequest(BaseModel):
    context: Dict[str, Any]

@app.post("/forecast")
async def generate_forecast(request: ForecastRequest):
    data_dicts = [{"date": item.date, "amount": item.amount} for item in request.historical_data]
    forecast_df = forecaster.generate_30_day_forecast(data_dicts)
    forecast_df['ds'] = forecast_df['ds'].dt.strftime('%Y-%m-%d')
    return {
        "entity": request.entity,
        "currency": request.currency,
        "forecast": forecast_df.to_dict(orient="records")
    }

@app.post("/simulate")
async def simulate_scenario(request: WhatIfRequest):
    results = apply_what_if_scenario(
        transactions=request.transactions,
        payment_delay_days=request.payment_delay_days,
        fx_rate_shift_percent=request.fx_rate_shift_percent,
        postpone_capex_days=request.postpone_capex_days
    )
    return {"simulated_transactions": results}

@app.post("/advisory")
async def get_advisory(request: AdvisoryRequest):
    result = advisory_engine.explain_gap_and_recommend(request.context)
    return result

@app.get("/health")
async def health():
    return {"status": "healthy"}
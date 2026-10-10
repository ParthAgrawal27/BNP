import pandas as pd
from typing import List, Dict

def apply_what_if_scenario(
        transactions: List[Dict],
        payment_delay_days: int = 0,
        fx_rate_shift_percent: float = 0.0,
        postpone_capex_days: int = 0
) -> List[Dict]:
    df = pd.DataFrame(transactions)
    if df.empty:
        return []

    df['transaction_date'] = pd.to_datetime(df['transaction_date'])
    df['simulated_date'] = df['transaction_date']
    df['simulated_amount'] = df['original_amount'].astype(float)

    # 1. Payment timing shift for regular outflows
    if payment_delay_days > 0:
        outflow_mask = (df['direction'] == 'Outflow') & (df['product_type'] != 'CAPEX')
        df.loc[outflow_mask, 'simulated_date'] = df.loc[outflow_mask, 'simulated_date'] + pd.Timedelta(days=payment_delay_days)

    # 2. CAPEX postponement
    if postpone_capex_days > 0:
        capex_mask = df['product_type'] == 'CAPEX'
        df.loc[capex_mask, 'simulated_date'] = df.loc[capex_mask, 'simulated_date'] + pd.Timedelta(days=postpone_capex_days)

    # 3. FX shock simulation
    if fx_rate_shift_percent != 0.0:
        multiplier = 1.0 + (fx_rate_shift_percent / 100.0)
        df['simulated_amount'] = df['simulated_amount'] * multiplier

    df['simulated_date'] = df['simulated_date'].dt.strftime('%Y-%m-%d')
    return df.to_dict(orient='records')
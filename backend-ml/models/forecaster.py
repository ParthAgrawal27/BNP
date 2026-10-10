import pandas as pd
from prophet import Prophet
from typing import List, Dict

class LiquidityForecaster:
    def __init__(self):
        self.model = Prophet(
            yearly_seasonality=True,
            weekly_seasonality=True,
            daily_seasonality=False,
            interval_width=0.95 # 95% confidence intervals
        )

    def generate_30_day_forecast(self, historical_data: List[Dict]) -> pd.DataFrame:
        """
        historical_data format: [{'date': '2023-01-01', 'amount': 1000000}, ...]
        """
        # Convert to Prophet required format (ds, y)
        df = pd.DataFrame(historical_data)
        df = df.rename(columns={'date': 'ds', 'amount': 'y'})
        df['ds'] = pd.to_datetime(df['ds'])

        # Fit model
        self.model.fit(df)

        # Create future dataframe for 30 days
        future = self.model.make_future_dataframe(periods=30)

        # Predict
        forecast = self.model.predict(future)

        # Extract the relevant 30-day forecast window
        future_forecast = forecast.tail(30)[['ds', 'yhat', 'yhat_lower', 'yhat_upper']]

        return future_forecast
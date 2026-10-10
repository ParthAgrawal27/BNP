from typing import Dict, Any

class TreasuryAdvisoryEngine:
    def explain_gap_and_recommend(self, context: Dict[str, Any]) -> Dict[str, Any]:
        gap = context.get("liquidityGap", 0)
        entity = context.get("entity", "Unknown")
        currency = context.get("currency", "USD")
        projected = context.get("projectedPosition", 0)
        threshold = context.get("threshold", 0)

        recommendations = []
        if gap < 0:
            shortfall = abs(gap)
            rationale = (
                f"Projected shortfall of {currency} {shortfall:,.2f} detected for {entity}. "
                f"Position ({currency} {projected:,.2f}) drops below required safety buffer of {currency} {threshold:,.2f}."
            )
            recommendations = [
                {"priority": 1, "action": "Delay Non-Urgent Outflows", "impact": "Conserves immediate buffer by postponing payments by 1-3 days"},
                {"priority": 2, "action": "Internal Cash Pooling Transfer", "impact": f"Move surplus funds from central treasury pool into {entity}"},
                {"priority": 3, "action": "Draw on Revolving Credit Facility", "impact": "Prevents costly overdraft penalties"}
            ]
        else:
            rationale = (
                f"{entity} holds a stable surplus of {currency} {gap:,.2f} above regulatory safety threshold."
            )
            if context.get("isDeployableSurplus", False):
                recommendations = [
                    {"priority": 1, "action": "Short-Term Overnight Deposit", "impact": "Earn yield on idle liquidity above the +20% threshold buffer"},
                    {"priority": 2, "action": "Intercompany Lending", "impact": "Fund entities operating in near-shortfall regions"}
                ]
            else:
                recommendations = [
                    {"priority": 1, "action": "Maintain Position", "impact": "Buffer is optimal; no reallocation necessary"}
                ]

        return {
            "entity": entity,
            "currency": currency,
            "rationale": rationale,
            "recommended_actions": recommendations
        }
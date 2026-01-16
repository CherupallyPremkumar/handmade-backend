import os

REPO_ROOT = "/Users/premkumar/homebase-backend.git"
TARGET_DIR = os.path.join(REPO_ROOT, "handmade-customer-management/handmade-customer-api/src/main/java/com/handmade/ecommerce/customer/model")

PAYLOADS = [
    "CancelSubscriptionPayload",
    "ExpireSubscriptionPayload",
    "ActivatePaidSubscriptionPayload",
    "StartTrialSubscriptionPayload",
    "EnterGraceSubscriptionPayload",
    "RenewSubscriptionPayload",
    "ActivateGiftCardPayload",
    "RedeemGiftCardPayload",
    "ExpireGiftCardPayload"
]

def create_payloads():
    if not os.path.exists(TARGET_DIR):
        os.makedirs(TARGET_DIR)
        
    for payload in PAYLOADS:
        file_path = os.path.join(TARGET_DIR, f"{payload}.java")
        content = f"""package com.handmade.ecommerce.customer.model;

import lombok.Data;

@Data
public class {payload} {{
}}
"""
        with open(file_path, 'w') as f:
            f.write(content)
        print(f"Created {payload}")

if __name__ == "__main__":
    create_payloads()

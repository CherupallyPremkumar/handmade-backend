import os

# Configuration
OWIZ_DIR = "/Users/premkumar/Desktop/handmade/handmade-app-core/handmade-owiz-workflows/src/main/resources/owiz"
VERSION = "1.1" # Amazon-Grade Resiliency Version

# OWIZ Flow Definitions
OWIZ_FLOWS = [
    {
        "id": "ORDER_PROCESSING_FLOW",
        "description": "Orchestrates order fulfillment after payment",
        "trigger": "PAY_ORDER",
        "steps": [
            {"id": "validateInventory", "componentName": "inventoryValidationService", "timeout": 3000},
            {"id": "deductStock", "componentName": "stockManagementService", "timeout": 5000},
            {"id": "createShipment", "componentName": "fulfillmentService", "timeout": 10000},
            {"id": "notifyCustomer", "componentName": "orderNotificationService", "timeout": 2000}
        ]
    },
    {
        "id": "RETURN_REFUND_FLOW",
        "description": "Orchestrates the return and refund process",
        "trigger": "APPROVE_RETURN",
        "steps": [
            {"id": "validateReturnedItem", "componentName": "returnQualityCheckService", "timeout": 5000},
            {"id": "processRefundPayment", "componentName": "paymentgatewayService", "timeout": 15000},
            {"id": "updateSellerBalance", "componentName": "ledgerService", "timeout": 5000},
            {"id": "notifyBuyer", "componentName": "refundNotificationService", "timeout": 2000}
        ]
    },
    {
        "id": "ONBOARDING_INIT_FLOW",
        "description": "Initiates seller vetting (Stripe KYC redirection)",
        "trigger": "START_ONBOARDING",
        "steps": [
            {"id": "verifyKyc", "componentName": "VerifyKycCommand", "timeout": 5000}
        ]
    },
    {
        "id": "ONBOARDING_RESUME_FLOW",
        "description": "Resumes vetting after external KYC verification",
        "trigger": "IDENTITY_VERIFIED",
        "steps": [
            {"id": "updateOnboardingState", "componentName": "UpdateOnboardingStateCommand", "timeout": 5000}
        ]
    },
    {
        "id": "ONBOARDING_CONFIRM_FLOW",
        "description": "Finalizes onboarding and creates seller account",
        "trigger": "CONFIRM_ONBOARDING",
        "steps": [
            {"id": "createSellerAccount", "componentName": "CreateSellerAccountCommand", "timeout": 10000},
            {"id": "sendWelcomeEmail", "componentName": "SendWelcomeEmailCommand", "timeout": 2000}
        ]
    },
    {
        "id": "FRAUD_CASE_FLOW",
        "description": "Orchestrates multi-layered fraud detection",
        "trigger": "OPEN_CASE",
        "steps": [
            {"id": "checkBuyerHistory", "componentName": "buyerRiskProfileService", "timeout": 3000},
            {"id": "checkSellerRating", "componentName": "sellerReputationService", "timeout": 3000},
            {"id": "applyRulesEngine", "componentName": "fraudRulesEngine", "timeout": 5000},
            {"id": "escalateToManual", "componentName": "manualReviewService", "timeout": 1000}
        ]
    },
    {
        "id": "PAYMENT_PAYOUT_FLOW",
        "description": "Orchestrates secure payment settlement",
        "trigger": "AUTHORIZE_PAYMENT",
        "steps": [
            {"id": "authorizePayment", "componentName": "paymentAuthService", "timeout": 10000},
            {"id": "capturePayment", "componentName": "paymentCaptureService", "timeout": 10000},
            {"id": "updateOrderState", "componentName": "orderStateUpdateService", "timeout": 3000},
            {"id": "triggerInvoice", "componentName": "billingService", "timeout": 5000}
        ]
    },
    {
        "id": "NOTIFICATIONS_FLOW",
        "description": "Orchestrates multi-channel notification dispatch",
        "trigger": "SEND_NOTIFICATION",
        "steps": [
            {"id": "preparePayload", "componentName": "notificationPayloadService", "timeout": 2000},
            {
                "type": "parallel",
                "id": "dispatch",
                "steps": [
                    {"id": "sendEmail", "componentName": "emailDispatchService", "timeout": 5000},
                    {"id": "sendPush", "componentName": "pushDispatchService", "timeout": 5000},
                    {"id": "sendSms", "componentName": "smsDispatchService", "timeout": 5000}
                ]
            }
        ]
    }
]

TEMPLATE = """<?xml version="1.0" encoding="UTF-8"?>
<!--
OWIZ Workflows
Description: Orchestrated transaction pipelines
Version: {version}
-->
<flows>
    <add-command-tag tag="parallel" componentName="org.chenile.owiz.impl.ParallelChain"/>
{flow_definitions}
</flows>
"""

FLOW_TEMPLATE = """    <flow id="{flow_id}" triggerEvent="{trigger}">
{commands}
    </flow>"""

COMMAND_TEMPLATE = """        <!-- Step: {id} -->
        <command id="{id}" componentName="{componentName}" retryLimit="3" retryDelay="1000" timeoutMs="{timeout}" onError="stop"/>"""

PARALLEL_TEMPLATE = """        <!-- Parallel Step: {id} -->
        <parallel id="{id}">
{sub_commands}
        </parallel>"""

def generate_flow_xml(flow):
    commands = []
    for step in flow["steps"]:
        if step.get("type") == "parallel":
            sub_commands = []
            for sub_step in step["steps"]:
                sub_commands.append("            " + COMMAND_TEMPLATE.format(
                    id=sub_step["id"],
                    componentName=sub_step["componentName"],
                    timeout=sub_step.get("timeout", 5000)
                ).strip())
            commands.append(PARALLEL_TEMPLATE.format(
                id=step["id"],
                sub_commands="\n".join(sub_commands)
            ))
        else:
            commands.append(COMMAND_TEMPLATE.format(
                id=step["id"],
                componentName=step["componentName"],
                timeout=step.get("timeout", 5000)
            ))
    
    return FLOW_TEMPLATE.format(
        flow_id=flow["id"],
        trigger=flow["trigger"],
        commands="\n".join(commands)
    )

def main():
    if not os.path.exists(OWIZ_DIR):
        os.makedirs(OWIZ_DIR)
        print(f"Created directory: {OWIZ_DIR}")

    # Grouping strategy for consolidated files
    grouped_flows = {
        "seller-onboarding-flow-owiz.xml": ["ONBOARDING_INIT_FLOW", "ONBOARDING_RESUME_FLOW", "ONBOARDING_CONFIRM_FLOW"],
        "order-processing-flow-owiz.xml": ["ORDER_PROCESSING_FLOW"],
        "return-refund-flow-owiz.xml": ["RETURN_REFUND_FLOW"],
        "fraud-case-flow-owiz.xml": ["FRAUD_CASE_FLOW"],
        "payment-payout-flow-owiz.xml": ["PAYMENT_PAYOUT_FLOW"],
        "notifications-flow-owiz.xml": ["NOTIFICATIONS_FLOW"]
    }

    flows_by_id = {f["id"]: f for f in OWIZ_FLOWS}

    for filename, flow_ids in grouped_flows.items():
        filepath = os.path.join(OWIZ_DIR, filename)
        print(f"Generating {filename}...")
        
        flow_defs = []
        for fid in flow_ids:
            if fid in flows_by_id:
                flow_defs.append(generate_flow_xml(flows_by_id[fid]))
        
        content = TEMPLATE.format(
            version=VERSION,
            flow_definitions="\n".join(flow_defs)
        )
        
        with open(filepath, "w") as f:
            f.write(content)
        print(f"Generated: {filepath}")

if __name__ == "__main__":
    main()

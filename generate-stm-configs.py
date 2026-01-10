import os

# Configuration
STM_DIR = "/Users/premkumar/Desktop/handmade/handmade-app-core/handmade-stm-management/src/main/resources/stm"

# STM Definitions (Amazon-Grade Strictness)
# Golden Rule: Policies are evaluated. Cases/Workflows are worked.
NEW_STMS = [
    {
        "filename": "seller-account-states.xml",
        "flow_id": "SELLER_ACCOUNT_FLOW",
        "entity": "Seller",
        "states": {
            "DRAFT": {
                "initial": True,
                "transitions": [("activate_account", "ACTIVE", "sellerActivateAction")]
            },
            "ACTIVE": {
                "transitions": [
                    ("suspend_account", "SUSPENDED", "sellerSuspendAction"),
                    ("terminate_account", "TERMINATED", "sellerTerminateAction")
                ]
            },
            "SUSPENDED": {
                "transitions": [
                    ("reactivate_account", "ACTIVE", "sellerReactivateAction"),
                    ("terminate_account", "TERMINATED", "sellerTerminateAction")
                ]
            },
            "TERMINATED": {"terminal": True}
        }
    },
    {
        "filename": "seller-kyc-states.xml",
        "flow_id": "SELLER_KYC_FLOW",
        "entity": "SellerVerification",
        "states": {
            "SUBMITTED": {
                "initial": True,
                "transitions": [("start_review", "IN_REVIEW", "kycStartReviewAction")]
            },
            "IN_REVIEW": {
                "transitions": [
                    ("verify_kyc", "VERIFIED", "kycVerifyAction"),
                    ("reject_kyc", "REJECTED", "kycRejectAction"),
                    ("request_info", "INFO_REQUESTED", "kycRequestInfoAction")
                ]
            },
            "INFO_REQUESTED": {
                "transitions": [("resubmit_info", "IN_REVIEW", "kycResubmitAction")]
            },
            "VERIFIED": {
                "transitions": [("revoke_verification", "REJECTED", "kycRevokeAction")]
            },
            "REJECTED": {"terminal": True}
        }
    },
    {
        "filename": "fraud-case-states.xml",
        "flow_id": "FRAUD_CASE_FLOW",
        "entity": "FraudCase",
        "states": {
            "OPEN": {
                "initial": True,
                "transitions": [("start_investigation", "UNDER_INVESTIGATION", "fraudStartInvestigationAction")]
            },
            "UNDER_INVESTIGATION": {
                "transitions": [
                    ("confirm_fraud", "ACTION_TAKEN", "fraudConfirmAction"),
                    ("dismiss_case", "CLOSED_NO_FRAUD", "fraudDismissAction"),
                    ("escalate_case", "ESCALATED", "fraudEscalateAction")
                ]
            },
            "ESCALATED": {
                "transitions": [
                    ("confirm_fraud", "ACTION_TAKEN", "fraudConfirmAction"),
                    ("dismiss_case", "CLOSED_NO_FRAUD", "fraudDismissAction")
                ]
            },
            "ACTION_TAKEN": {"terminal": True},
            "CLOSED_NO_FRAUD": {"terminal": True}
        }
    },
    {
        "filename": "order-states.xml",
        "flow_id": "ORDER_FLOW",
        "entity": "Order",
        "states": {
            "CREATED": {
                "initial": True,
                "transitions": [
                    ("pay_order", "PAID", "orderPaymentAction"),
                    ("cancel_order", "CANCELLED", "orderCancelAction")
                ]
            },
            "PAID": {
                "transitions": [
                    ("mark_shipped", "SHIPPED", "orderShipAction")
                ]
            },
            "SHIPPED": {
                "transitions": [
                    ("confirm_delivery", "COMPLETED", "orderCompleteAction")
                ]
            },
            "COMPLETED": {"terminal": True},
            "CANCELLED": {"terminal": True}
        }
    },
    {
        "filename": "return-request-states.xml",
        "flow_id": "RETURN_REQUEST_FLOW",
        "entity": "ReturnRequest",
        "states": {
            "REQUESTED": {
                "initial": True,
                "transitions": [
                    ("approve_return", "APPROVED", "returnApproveAction"),
                    ("reject_return", "REJECTED", "returnRejectAction")
                ]
            },
            "APPROVED": {
                "transitions": [("receive_item", "RECEIVED", "returnReceiveAction")]
            },
            "RECEIVED": {
                "transitions": [("process_refund", "REFUNDED", "returnRefundAction")]
            },
            "REFUNDED": {"terminal": True},
            "REJECTED": {"terminal": True}
        }
    },
    {
        "filename": "payment-states.xml",
        "flow_id": "PAYMENT_FLOW",
        "entity": "PaymentTransaction",
        "states": {
            "INIT": {
                "initial": True,
                "transitions": [
                    ("authorize_payment", "AUTH_SUCCESS", "paymentAuthAction"),
                    ("fail_payment", "FAILED", "paymentFailAction")
                ]
            },
            "AUTH_SUCCESS": {
                "transitions": [
                    ("capture_payment", "CAPTURED", "paymentCaptureAction"),
                    ("void_payment", "VOIDED", "paymentVoidAction")
                ]
            },
            "CAPTURED": {"terminal": True},
            "FAILED": {"terminal": True},
            "VOIDED": {"terminal": True}
        }
    },
    {
        "filename": "refund-states.xml",
        "flow_id": "REFUND_FLOW",
        "entity": "Refund",
        "states": {
            "REQUESTED": {
                "initial": True,
                "transitions": [
                    ("approve_refund", "APPROVED", "refundApproveAction"),
                    ("reject_refund", "REJECTED", "refundRejectAction")
                ]
            },
            "APPROVED": {
                "transitions": [("execute_payment", "PAID", "refundPaymentAction")]
            },
            "PAID": {"terminal": True},
            "REJECTED": {"terminal": True}
        }
    },
    {
        "filename": "payout-states.xml",
        "flow_id": "PAYOUT_FLOW",
        "entity": "Payout",
        "states": {
            "CREATED": {
                "initial": True,
                "transitions": [
                    ("initiate_payout", "PROCESSING", "payoutInitiateAction"),
                    ("fail_payout", "FAILED", "payoutFailAction")
                ]
            },
            "PROCESSING": {
                "transitions": [
                    ("confirm_payout", "PAID", "payoutCompleteAction"),
                    ("fail_payout", "FAILED", "payoutFailAction")
                ]
            },
            "PAID": {"terminal": True},
            "FAILED": {"terminal": True}
        }
    },
    {
        "filename": "pick-task-states.xml",
        "flow_id": "PICK_TASK_FLOW",
        "entity": "PickTask",
        "states": {
            "CREATED": {
                "initial": True,
                "transitions": [("assign_picker", "ASSIGNED", "pickAssignAction")]
            },
            "ASSIGNED": {
                "transitions": [
                    ("confirm_pick", "PICKED", "pickCompleteAction"),
                    ("report_missing", "FAILED", "pickFailAction")
                ]
            },
            "PICKED": {"terminal": True},
            "FAILED": {"terminal": True}
        }
    },
    {
        "filename": "pack-task-states.xml",
        "flow_id": "PACK_TASK_FLOW",
        "entity": "PackTask",
        "states": {
            "CREATED": {
                "initial": True,
                "transitions": [("start_packing", "PACKING", "packStartAction")]
            },
            "PACKING": {
                "transitions": [("seal_box", "SEALED", "packSealAction")]
            },
            "SEALED": {
                "transitions": [("mark_ready", "SHIPPED", "packReadyAction")]
            },
            "SHIPPED": {"terminal": True}
        }
    },
    {
        "filename": "delivery-attempt-states.xml",
        "flow_id": "DELIVERY_ATTEMPT_FLOW",
        "entity": "DeliveryAttempt",
        "states": {
            "ATTEMPTED": {
                "initial": True,
                "transitions": [
                    ("mark_delivered", "DELIVERED", "deliverySuccessAction"),
                    ("mark_failed", "FAILED", "deliveryFailAction")
                ]
            },
            "FAILED": {
                "transitions": [("reschedule", "RESCHEDULED", "deliveryRescheduleAction")]
            },
            "DELIVERED": {"terminal": True},
            "RESCHEDULED": {"terminal": True}
        }
    },
    {
        "filename": "gdpr-request-states.xml",
        "flow_id": "GDPR_REQUEST_FLOW",
        "entity": "GdprRequest",
        "states": {
            "REQUESTED": {
                "initial": True,
                "transitions": [("start_processing", "PROCESSING", "gdprStartAction")]
            },
            "PROCESSING": {
                "transitions": [
                    ("complete_request", "COMPLETED", "gdprCompleteAction"),
                    ("fail_request", "FAILED", "gdprFailAction"),
                    ("cancel_request", "CANCELLED", "gdprCancelAction")
                ]
            },
            "COMPLETED": {"terminal": True},
            "FAILED": {"terminal": True},
            "CANCELLED": {"terminal": True}
        }
    },
    {
        "filename": "seller-store-states.xml",
        "flow_id": "SELLER_STORE_FLOW",
        "entity": "SellerStore",
        "states": {
            "DRAFT": {
                "initial": True,
                "transitions": [("publish_store", "LIVE", "storeGoLiveAction")]
            },
            "LIVE": {
                "transitions": [
                    ("suspend_store", "SUSPENDED", "storeSuspendAction"),
                    ("enter_maintenance", "MAINTENANCE", "storeEnterMaintenanceAction")
                ]
            },
            "SUSPENDED": {
                "transitions": [("resume_store", "LIVE", "storeExitMaintenanceAction")]
            },
            "MAINTENANCE": {
                "transitions": [("resume_store", "LIVE", "storeExitMaintenanceAction")]
            }
        }
    },
    {
        "filename": "onboarding-states.xml",
        "flow_id": "onboarding",
        "entity": "SellerOnboardingCase",
        "states": {
            "DRAFT": {
                "initial": True,
                "transitions": [("start-onboarding", "INITIALIZED", "startOnboardingAction")]
            },
            "INITIALIZED": {
                "transitions": [
                    ("identity-verified", "VERIFIED_PENDING_DOCS", "identityVerifiedAction"),
                    ("reject", "REJECTED", "rejectOnboardingAction")
                ]
            },
            "VERIFIED_PENDING_DOCS": {
                "transitions": [
                    ("confirm-onboarding", "COMPLETED", "confirmOnboardingAction"),
                    ("submit-docs", "VERIFIED_PENDING_DOCS", "submitDocsAction")
                ]
            },
            "COMPLETED": {"terminal": True},
            "REJECTED": {"terminal": True}
        }
    }
]

TEMPLATE = """<?xml version="1.0" encoding="UTF-8"?>
<!--
{entity} State Machine
Purpose: Manage the lifecycle of {entity} entities
Flow: {flow_id}
-->
<states>
{event_info}
    <flow id="{flow_id}" default="true">
{state_definitions}
    </flow>
</states>
"""

EVENT_INFO_TEMPLATE = """    <event-information eventId='{event_id}' 
                       eventName='{event_name}' 
                       meta-acls="user.normal,user.admin"/>
"""

STATE_TEMPLATE = """        <manual-state id="{id}"{initial}{terminal}>
{transitions}
        </manual-state>
"""

TRANSITION_TEMPLATE = """            <on eventId="{event_id}" newStateId="{new_state}" componentName="{component}"/>"""

def generate_stm_xml(stm_def):
    events = set()
    for state_id, state_info in stm_def["states"].items():
        for event_id, new_state, component in state_info.get("transitions", []):
            events.add(event_id)
    
    event_info_list = []
    for event_id in sorted(list(events)):
        event_info_list.append(EVENT_INFO_TEMPLATE.format(
            event_id=event_id,
            event_name=event_id.replace("_", " ").capitalize()
        ))
    
    state_def_list = []
    for state_id, state_info in stm_def["states"].items():
        transitions = []
        for event_id, new_state, component in state_info.get("transitions", []):
            transitions.append(TRANSITION_TEMPLATE.format(
                event_id=event_id,
                new_state=new_state,
                component=component
            ))
        
        initial_str = ' initialState="true"' if state_info.get("initial") else ""
        terminal_str = ' meta-endState="true"' if state_info.get("terminal") else ""
        
        state_def_list.append(STATE_TEMPLATE.format(
            id=state_id,
            initial=initial_str,
            terminal=terminal_str,
            transitions="\n".join(transitions)
        ))
        
    return TEMPLATE.format(
        entity=stm_def["entity"],
        flow_id=stm_def["flow_id"],
        event_info="".join(event_info_list),
        state_definitions="".join(state_def_list)
    )

def main():
    if not os.path.exists(STM_DIR):
        print(f"Error: Directory {STM_DIR} does not exist.")
        return

    for stm in NEW_STMS:
        filepath = os.path.join(STM_DIR, stm["filename"])
        print(f"Generating {stm['filename']}...")
        content = generate_stm_xml(stm)
        with open(filepath, "w") as f:
            f.write(content)
        print(f"Generated: {filepath}")

    # Final Purge of all policy-related STMs
    all_files = os.listdir(STM_DIR)
    for filename in all_files:
        if filename.endswith("policy-states.xml"):
            path = os.path.join(STM_DIR, filename)
            os.remove(path)
            print(f"Purged policy STM: {path}")

if __name__ == "__main__":
    main()

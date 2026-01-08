# Schema Gap Report: Amazon-Grade Infrastructure

This report identifies missing tables based on the "Amazon-Grade" production readiness checklist.

## 1. Search & Recommendation
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_search_index_version` | **MISSING** | Create for index lifecycle management |
| `hm_product_popularity_signal`| **PARTIAL** | Specialize `hm_ranking_signal` or create new |
| `hm_user_behavior_signal` | **PARTIAL** | Link with `hm_browse_history` |
| `hm_recommendation_graph` | **EXISTS** | `hm_recommendation_graph` |

## 2. Event-Driven Architecture
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_event_queue` | **EXISTS** | `hm_event_queue` |
| `hm_event_subscription` | **EXISTS** | `hm_event_subscription` |
| `hm_job_scheduler` | **EXISTS** | `hm_job_scheduler` |
| `hm_workflow_task` | **EXISTS** | `hm_workflow_task` |

## 3. Risk, Fraud, and Compliance
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_fraud_signal` | **MISSING** | Create for raw signal ingestion |
| `hm_seller_trust_score` | **EXISTS** | `hm_seller_trust_score` |
| `hm_compliance_document` | **EXISTS** | `hm_compliance_document` |
| `hm_fraud_case` | **EXISTS** | `hm_fraud_case` |

## 4. Platform & Feature Management
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_platform_feature_flag` | **EXISTS** | `hm_platform_feature` |
| `hm_platform_region_policy` | **EXISTS** | `hm_platform_policy` |
| `hm_platform_activity_log` | **EXISTS** | `hm_platform_activity_log` |

## 5. Analytics & Reporting
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_sales_analytics_snapshot`| **EXISTS** | `hm_sales_analytics_snapshot` |
| `hm_ad_performance_daily` | **EXISTS** | `hm_ad_performance_daily` |
| `hm_inventory_snapshot` | **EXISTS** | `hm_inventory_snapshot` |
| `hm_order_summary` | **MISSING** | Create for pre-aggregated BI |

## 6. Notifications & Communication
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_notification_queue` | **MISSING** | Create for async delivery |
| `hm_notification_template` | **EXISTS** | `hm_notification_template` |
| `hm_user_preference` | **EXISTS** | `hm_user_preference` |

## 7. Quotas & Limits
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_tenant_quota` | **EXISTS** | `hm_tenant_quota` |
| `hm_api_rate_limit` | **EXISTS** | `hm_api_rate_limit` |

## 8. Experimentation
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_experiment` | **EXISTS** | `hm_experiment` |
| `hm_experiment_variant` | **EXISTS** | `hm_experiment_variant` |
| `hm_experiment_result` | **EXISTS** | `hm_experiment_result` |

## 9. Observability
| Requirement | Status | Current Table / Action |
| :--- | :--- | :--- |
| `hm_error_log` | **MISSING** | Create for system health |
| `hm_metrics_snapshot` | **MISSING** | Create for operational KPIs |
| `hm_activity_stream` | **MISSING** | Create as unified stream |

#!/bin/bash

# Cloud Run Deployment Script for Keycloak + Backend

set -e

# Configuration
PROJECT_ID=${GCP_PROJECT_ID:-"your-project-id"}
REGION=${GCP_REGION:-"us-central1"}
SERVICE_NAME_BACKEND="terra-dwell-backend"
SERVICE_NAME_KEYCLOAK="terra-dwell-keycloak"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}Starting deployment to Cloud Run...${NC}"

# Check if gcloud is installed
if ! command -v gcloud &> /dev/null; then
    echo -e "${RED}gcloud CLI is not installed. Please install it first.${NC}"
    exit 1
fi

# Set project
echo -e "${BLUE}Setting GCP project to ${PROJECT_ID}...${NC}"
gcloud config set project $PROJECT_ID

# Enable required APIs
echo -e "${BLUE}Enabling required APIs...${NC}"
gcloud services enable \
    run.googleapis.com \
    cloudbuild.googleapis.com \
    containerregistry.googleapis.com \
    sqladmin.googleapis.com

# Create Cloud SQL instance (if not exists)
echo -e "${BLUE}Checking Cloud SQL instance...${NC}"
if ! gcloud sql instances describe terra-dwell-postgres --quiet 2>/dev/null; then
    echo -e "${BLUE}Creating Cloud SQL PostgreSQL instance...${NC}"
    gcloud sql instances create terra-dwell-postgres \
        --database-version=POSTGRES_15 \
        --tier=db-f1-micro \
        --region=$REGION \
        --root-password=$(openssl rand -base64 32)
    
    # Create databases
    gcloud sql databases create keycloak --instance=terra-dwell-postgres
    gcloud sql databases create ecommerce --instance=terra-dwell-postgres
fi

# Get Cloud SQL connection name
SQL_CONNECTION=$(gcloud sql instances describe terra-dwell-postgres --format='value(connectionName)')

# Build and deploy Keycloak
echo -e "${BLUE}Building and deploying Keycloak...${NC}"
gcloud builds submit --tag gcr.io/$PROJECT_ID/$SERVICE_NAME_KEYCLOAK \
    --file=Dockerfile.keycloak .

gcloud run deploy $SERVICE_NAME_KEYCLOAK \
    --image gcr.io/$PROJECT_ID/$SERVICE_NAME_KEYCLOAK \
    --platform managed \
    --region $REGION \
    --allow-unauthenticated \
    --port 8080 \
    --memory 1Gi \
    --cpu 1 \
    --min-instances 1 \
    --max-instances 3 \
    --add-cloudsql-instances $SQL_CONNECTION \
    --set-env-vars "KC_DB=postgres,KC_DB_URL=jdbc:postgresql:///keycloak?cloudSqlInstance=$SQL_CONNECTION&socketFactory=com.google.cloud.sql.postgres.SocketFactory,KC_HOSTNAME_STRICT=false,KC_HTTP_ENABLED=true,KC_PROXY=edge" \
    --set-secrets "KC_DB_PASSWORD=keycloak-db-password:latest,KEYCLOAK_ADMIN_PASSWORD=keycloak-admin-password:latest"

# Get Keycloak URL
KEYCLOAK_URL=$(gcloud run services describe $SERVICE_NAME_KEYCLOAK \
    --platform managed \
    --region $REGION \
    --format 'value(status.url)')

echo -e "${GREEN}Keycloak deployed at: $KEYCLOAK_URL${NC}"

# Build and deploy Backend
echo -e "${BLUE}Building and deploying Backend...${NC}"
gcloud builds submit --tag gcr.io/$PROJECT_ID/$SERVICE_NAME_BACKEND \
    --file=Dockerfile.backend .

gcloud run deploy $SERVICE_NAME_BACKEND \
    --image gcr.io/$PROJECT_ID/$SERVICE_NAME_BACKEND \
    --platform managed \
    --region $REGION \
    --allow-unauthenticated \
    --port 8080 \
    --memory 1Gi \
    --cpu 1 \
    --min-instances 0 \
    --max-instances 10 \
    --add-cloudsql-instances $SQL_CONNECTION \
    --set-env-vars "SPRING_PROFILES_ACTIVE=prod,KEYCLOAK_AUTH_SERVER_URL=$KEYCLOAK_URL,SPRING_DATASOURCE_URL=jdbc:postgresql:///ecommerce?cloudSqlInstance=$SQL_CONNECTION&socketFactory=com.google.cloud.sql.postgres.SocketFactory" \
    --set-secrets "SPRING_DATASOURCE_PASSWORD=keycloak-db-password:latest,KEYCLOAK_CREDENTIALS_SECRET=keycloak-client-secret:latest"

# Get Backend URL
BACKEND_URL=$(gcloud run services describe $SERVICE_NAME_BACKEND \
    --platform managed \
    --region $REGION \
    --format 'value(status.url)')

echo -e "${GREEN}Backend deployed at: $BACKEND_URL${NC}"

# Summary
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}Deployment completed successfully!${NC}"
echo -e "${GREEN}========================================${NC}"
echo -e "Keycloak URL: ${BLUE}$KEYCLOAK_URL${NC}"
echo -e "Backend URL: ${BLUE}$BACKEND_URL${NC}"
echo -e "Keycloak Admin: ${BLUE}$KEYCLOAK_URL/admin${NC}"
echo -e "${GREEN}========================================${NC}"

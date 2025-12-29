#!/bin/bash

# Deploy Handmade Backend to Local Kubernetes
# This script deploys the application to a local Kubernetes cluster (Minikube, Kind, Docker Desktop)

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
    print_error "kubectl is not installed. Please install kubectl first."
    exit 1
fi

# Check if kustomize is installed
if ! command -v kustomize &> /dev/null; then
    print_warning "kustomize is not installed. Using kubectl with -k flag instead."
    USE_KUSTOMIZE=false
else
    USE_KUSTOMIZE=true
fi

# Detect local Kubernetes cluster
print_info "Detecting local Kubernetes cluster..."
CONTEXT=$(kubectl config current-context)
print_info "Current context: $CONTEXT"

if [[ "$CONTEXT" == *"minikube"* ]]; then
    CLUSTER_TYPE="minikube"
    print_info "Detected Minikube cluster"
elif [[ "$CONTEXT" == *"kind"* ]]; then
    CLUSTER_TYPE="kind"
    print_info "Detected Kind cluster"
elif [[ "$CONTEXT" == *"docker-desktop"* ]] || [[ "$CONTEXT" == *"docker-for-desktop"* ]]; then
    CLUSTER_TYPE="docker-desktop"
    print_info "Detected Docker Desktop cluster"
else
    print_warning "Unknown cluster type. Proceeding anyway..."
    CLUSTER_TYPE="unknown"
fi

# Build Docker image
print_info "Building Docker image..."
cd ../../../app-boot/build-package

if [ "$CLUSTER_TYPE" == "minikube" ]; then
    print_info "Setting Docker environment for Minikube..."
    eval $(minikube docker-env)
fi

# Build the application
print_info "Building application with Maven..."
mvn clean package -DskipTests

# Build Docker image
print_info "Building Docker image: handmade-backend:local-latest"
docker build -t handmade-backend:local-latest .

if [ "$CLUSTER_TYPE" == "kind" ]; then
    print_info "Loading image into Kind cluster..."
    kind load docker-image handmade-backend:local-latest
fi

print_success "Docker image built successfully"

# Go back to k8s directory
cd ../../k8s-deployment/overlays/local

# Deploy to Kubernetes
print_info "Deploying to Kubernetes..."

if [ "$USE_KUSTOMIZE" = true ]; then
    kustomize build . | kubectl apply -f -
else
    kubectl apply -k .
fi

print_success "Deployment applied successfully"

# Wait for deployment to be ready
print_info "Waiting for deployment to be ready..."
kubectl wait --for=condition=available --timeout=300s deployment/local-handmade-backend -n handmade-local

print_success "Deployment is ready!"

# Get service information
print_info "Getting service information..."
kubectl get svc -n handmade-local

# Get pod information
print_info "Getting pod information..."
kubectl get pods -n handmade-local

# Print access information
echo ""
print_success "=== Deployment Complete ==="
echo ""
print_info "Access the application:"

if [ "$CLUSTER_TYPE" == "minikube" ]; then
    MINIKUBE_IP=$(minikube ip)
    echo "  - Application: http://$MINIKUBE_IP:30080"
    echo "  - Actuator: http://$MINIKUBE_IP:30081/actuator"
    echo "  - H2 Console: http://$MINIKUBE_IP:30080/h2-console"
    echo "  - Swagger UI: http://$MINIKUBE_IP:30080/swagger-ui.html"
    echo "  - Debug Port: $MINIKUBE_IP:30005"
elif [ "$CLUSTER_TYPE" == "docker-desktop" ]; then
    echo "  - Application: http://localhost:30080"
    echo "  - Actuator: http://localhost:30081/actuator"
    echo "  - H2 Console: http://localhost:30080/h2-console"
    echo "  - Swagger UI: http://localhost:30080/swagger-ui.html"
    echo "  - Debug Port: localhost:30005"
elif [ "$CLUSTER_TYPE" == "kind" ]; then
    echo "  - Application: http://localhost:30080"
    echo "  - Actuator: http://localhost:30081/actuator"
    echo "  - H2 Console: http://localhost:30080/h2-console"
    echo "  - Swagger UI: http://localhost:30080/swagger-ui.html"
    echo "  - Debug Port: localhost:30005"
    print_info "Note: You may need to set up port forwarding for Kind"
fi

echo ""
print_info "Useful commands:"
echo "  - View logs: kubectl logs -f -l app=handmade-backend -n handmade-local"
echo "  - Describe pod: kubectl describe pod -l app=handmade-backend -n handmade-local"
echo "  - Port forward: kubectl port-forward svc/local-handmade-backend 8080:80 -n handmade-local"
echo "  - Delete deployment: kubectl delete -k ."
echo ""

print_success "Happy coding! 🚀"

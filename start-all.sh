#!/bin/bash

set -e

# Start external dependencies
echo "🚀 Starting Docker Compose services..."
docker-compose up -d

# === Install parent POM ===
echo "🔧 Installing parent project (multi-tenant-saas)..."
mvn clean install -N
echo "✅ Parent POM installed."

# === Build shared module ===
echo "🔧 Building shared module..."
(cd shared && mvn clean install)
echo "✅ Shared module built successfully."

# Function to wait for a service to become healthy
wait_for_health() {
  local name=$1
  local port=$2

  echo "⏳ Waiting for $name on port $port..."
  until curl -s "http://localhost:$port/q/health/ready" | grep '"status":"UP"' > /dev/null; do
    echo " → $name not ready yet..."
    sleep 2
  done
  echo "✅ $name is ready!"
}

# Start each Quarkus module in the background
start_module() {
  local module=$1
  echo "🚀 Starting $module..."
  (cd "$module" && mvn quarkus:dev &)
}

# === Start modules ===
start_module "trainer-module"
start_module "client-module"
start_module "workout-module"
start_module "nutrition-module"
start_module "organization-module"
start_module "security-module"

# === Wait for health checks ===
wait_for_health "Trainer Module" 8081
wait_for_health "Client Module" 8082
wait_for_health "Workout Module" 8083
wait_for_health "Nutrition Module" 8084
wait_for_health "Organization Module" 8085
wait_for_health "Security Module" 8086

echo "✅ All services are up and running."

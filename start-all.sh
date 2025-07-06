#!/bin/bash

set -e

# Start external dependencies
echo "Starting Docker Compose services..."
docker-compose up -d

# === Install parent POM ===
echo "Installing parent project (multi-tenant-saas)..."
mvn clean install -N
echo "Parent POM installed."

# === Build shared module ===
echo "Building shared module..."
(cd shared && mvn clean install)
echo "Shared module built successfully."

# Function to wait for a service to become healthy
wait_for_health() {
  local name=$1
  local port=$2

  echo "⏳ Waiting for $name on port $port..."
  until powershell -Command "(Invoke-WebRequest -UseBasicParsing http://localhost:$port/q/health/ready).Content -match '\"status\":\"UP\"'" > /dev/null; do
    echo " → $name not ready yet..."
    sleep 2
  done
  echo "✅ $name is ready!"
}

# Function to start a module and wait for its readiness
start_and_wait() {
  local module=$1
  local port=$2

  echo "Starting $module..."
  (cd "$module" && mvn quarkus:dev -Dquarkus.http.port=$port &)  # start in background
  wait_for_health "$module" "$port"
}

# === Start and wait each module ===
start_and_wait "trainer-module" 8081
start_and_wait "client-module" 8082
start_and_wait "workout-module" 8083
start_and_wait "nutrition-module" 8084
start_and_wait "organization-module" 8085
start_and_wait "security-module" 8086

echo "All services are up and running."

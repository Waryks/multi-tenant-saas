#!/bin/sh

echo "Stopping all services..."

# === Stop Quarkus services (Java processes started via Maven) ===
echo "Looking for Java processes started by Maven..."
for pid in $(tasklist //FI "IMAGENAME eq java.exe" //FO LIST | grep "PID:" | awk '{print $2}'); do
  echo "Killing Java process with PID $pid"
  taskkill //F //PID "$pid"
done

# === Stop Docker Compose containers ===
echo "Stopping Docker Compose containers..."
docker-compose down

echo "All services stopped."

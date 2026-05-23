#!/usr/bin/env bash

echo "Stopping servers..."
# Attempt graceful stop via queries to process; fallback to pkill
pkill -f "java -jar server.jar" || true
sleep 1
pkill -9 -f "java -jar server.jar" || true
echo "Stopped."

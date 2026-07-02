#!/usr/bin/env bash
set -euo pipefail
base="$(cd "$(dirname "$0")" && pwd)"

stop_service() {
    name="$1"
    dir="$2"
    absdir="$(cd "$base/$dir" && pwd)"
    stopped=0

    for pid in $(pgrep -x java || true); do
        cwd="$(readlink -f /proc/$pid/cwd 2>/dev/null || true)"
        if [ "$cwd" = "$absdir" ]; then
            echo "Stopping $name (pid $pid)..."
            kill "$pid" || true
            sleep 2
            if ps -p "$pid" > /dev/null 2>&1; then
                echo "Force-killing $name (pid $pid)..."
                kill -9 "$pid" || true
                sleep 1
            fi
            stopped=1
        fi
    done

    if [ "$stopped" -eq 0 ]; then
        echo "$name is not running"
    fi
}

stop_service "Limbo" "limbo"
stop_service "Velocity" "velocity"
stop_service "Server" "server"

echo "Stop complete."

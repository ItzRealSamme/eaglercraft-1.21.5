#!/usr/bin/env bash
set -euo pipefail
base="$(cd "$(dirname "$0")" && pwd)"

check_service() {
    name="$1"
    dir="$2"
    port="$3"
    absdir="$(cd "$base/$dir" && pwd)"
    running=0
    pids=()

    for pid in $(pgrep -x java || true); do
        cwd="$(readlink -f /proc/$pid/cwd 2>/dev/null || true)"
        if [ "$cwd" = "$absdir" ]; then
            pids+=("$pid")
            running=1
        fi
    done

    if [ "$running" -eq 1 ]; then
        printf "%-8s: running (pid=%s, port=%s)\n" "$name" "${pids[*]}" "$port"
    else
        printf "%-8s: stopped\n" "$name"
    fi
}

check_service "Limbo" "limbo" 25566
check_service "Velocity" "velocity" 25567
check_service "Server" "server" 25565

echo
ss -ltnp 2>/dev/null | grep -E ':(25565|25566|25567)\b' || true

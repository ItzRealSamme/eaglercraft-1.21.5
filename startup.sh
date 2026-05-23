#!/usr/bin/env bash

start_if_free() {
	name="$1"
	dir="$2"
	port="$3"
	if ss -ltnp 2>/dev/null | grep -q -E ":$port\b"; then
		echo "$name already running (port $port in use), skipping"
	else
		echo "Starting $name..."
		logfile="/tmp/${name// /_}-server-start.log"
		(cd "$dir" && nohup java -jar server.jar >"$logfile" 2>&1 &)
		sleep 1
	fi
}

start_if_free "Limbo" "limbo" 25566
start_if_free "Velocity" "velocity" 25567
start_if_free "Server" "server" 25565

echo "All start attempts complete. Check logs for details."

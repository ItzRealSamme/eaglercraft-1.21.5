#!/usr/bin/env bash

start_if_free() {
	name="$1"
	dir="$2"
	port="$3"
	# resolve absolute directory
	absdir="$(cd "$dir" && pwd)"

	# If port is in use, assume service running
	if ss -ltnp 2>/dev/null | grep -q -E ":$port\b"; then
		echo "$name already running (port $port in use), skipping"
		return
	fi

	# Check for any java process whose current working directory is the target dir
	for pid in $(pgrep -x java || true); do
		cwd="$(readlink -f /proc/$pid/cwd 2>/dev/null || true)"
		if [ "$cwd" = "$absdir" ]; then
			echo "$name already running (process $pid in $dir), skipping"
			return
		fi
	done

	echo "Starting $name..."
	logfile="/tmp/${name// /_}-server-start.log"
	(cd "$dir" && nohup java -jar server.jar >"$logfile" 2>&1 &)
	sleep 1
}

start_if_free "Limbo" "limbo" 25566
start_if_free "Velocity" "velocity" 25567
start_if_free "Server" "server" 25565

echo "All start attempts complete. Check logs for details."

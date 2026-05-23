Deployment
==========

This repository can be deployed as a single Docker container that runs the
proxy (`velocity`), `limbo`, and the main Paper `server` via `startup.sh`.

Build and run with Docker Compose:

```bash
docker compose build --pull --no-cache
docker compose up -d
```

Logs:

```bash
docker compose logs -f
```

Stopping:

```bash
docker compose down
```

Notes:
- The Dockerfile uses Java 25 to match the runtime used during local testing.
- If your host uses a different Java version, update `Dockerfile` accordingly.
- The container mounts the `server`, `limbo`, and `velocity` directories for
  persistent data; adjust the `docker-compose.yml` volumes if you prefer host
  paths elsewhere.

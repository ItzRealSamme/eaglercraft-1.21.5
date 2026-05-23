FROM eclipse-temurin:25-jdk

WORKDIR /app
COPY . .
RUN chmod +x startup.sh stop.sh || true

# Expose Minecraft / proxy ports
EXPOSE 25565 25566 25567

CMD ["bash", "startup.sh"]
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN chmod +x startup.sh
CMD ["bash", "startup.sh"] 
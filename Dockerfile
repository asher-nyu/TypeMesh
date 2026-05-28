FROM node:20 AS frontend
WORKDIR /app
COPY package*.json ./
COPY frontend/package*.json frontend/
RUN npm install
COPY frontend frontend
RUN npm run frontend:build

FROM maven:3.9.9-eclipse-temurin-21 AS backend
WORKDIR /app
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src
COPY --from=frontend /app/frontend/dist backend/src/main/resources/static
WORKDIR /app/backend
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=backend /app/backend/target/backend-0.1.0-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]

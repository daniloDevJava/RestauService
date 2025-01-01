# Étape 1 : Construire l'application
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Définir le répertoire de travail pour Maven
WORKDIR /apiGateway

# Copier le fichier de configuration Maven et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source de l'application
COPY src ./src

# Compiler et créer le fichier .jar
RUN mvn clean package -DskipTests

# Étape 2 : Construire une image légère pour exécuter l'application
FROM openjdk:21-jdk-slim

# Définir le répertoire de travail pour l'application
WORKDIR /apiGateway

# Copier le fichier .jar produit dans l'étape précédente
COPY --from=builder /apiGateway/target/apiGateway-0.0.1.jar apiGateway.jar

# Exposer le port défini dans votre application Spring Boot
EXPOSE 6005

# Commande pour exécuter l'application
CMD ["java", "-jar", "apiGateway.jar"]

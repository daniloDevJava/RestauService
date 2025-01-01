# Étape 1 : Construire l'application à partir d'une image commune
FROM dao:latest AS builder

# Copier uniquement le code source spécifique à ce projet
WORKDIR /foodGo-Connexion-Comptes-Users
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Construire une image légère pour exécuter l'application
FROM openjdk:21-jdk-slim

# Créer un utilisateur non root
RUN useradd -m -s /bin/bash appuser

# Définir le répertoire de travail pour l'application
WORKDIR /foodGo-Connexion-Comptes-Users

# Copier le fichier .jar produit dans l'étape précédente
COPY --from=builder /foodGo-Connexion-Comptes-Users/target/useraccount-0.0.1.jar useraccount.jar

# Changer l'utilisateur courant pour appuser
USER appuser

# Définir les variables d'environnement pour la connexion à PostgreSQL
ENV DB_HOST=postgres-container
ENV DB_PORT=5432
ENV DB_NAME=useraccount
ENV DB_USER=dan
ENV DB_PASSWORD=dyba

# Exposer le port défini dans votre application Spring Boot
EXPOSE 6002

# Commande pour exécuter l'application
CMD ["java", "-jar", "useraccount.jar"]


# Dockerfile for payment (service_payment)
FROM python:3.10-slim

WORKDIR /app

# Installer les dépendances système nécessaires (ajoutez cette ligne si nécessaire)
# RUN apt-get update && apt-get install -y <paquet-système> && rm -rf /var/lib/apt/lists/*

# Copier le fichier requirements.txt et installer les dépendances Python
COPY requirements.txt .
RUN pip install --upgrade pip && pip install --no-cache-dir -r requirements.txt

# Copier le reste de l'application
COPY . .

# Expose the port used by the service
EXPOSE 5002

CMD ["python3", "app.py"]
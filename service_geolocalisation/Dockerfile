# Dockerfile for geolocalisation (service_geolocalisation)
FROM python:3.10-slim

WORKDIR /app

# Installer les dépendances système nécessaires
RUN apt-get update && apt-get install -y apt-xapian-index && rm -rf /var/lib/apt/lists/*

# Copier le fichier requirements.txt et installer les dépendances Python
COPY requirements.txt .
RUN pip install --upgrade pip && pip install --no-cache-dir -r requirements.txt

# Copier le reste de l'application
COPY . .

# Expose the port used by the service
EXPOSE 5001

CMD ["python3", "app.py"]
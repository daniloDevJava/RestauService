# Dockerfile for geolocalisation (service_geolocalisation)
FROM python:3.10-slim

WORKDIR /app/service_geolocalisation

ENV PYTHONPATH="${PYTHONPATH}:/home/Appli_FoodGo/RestauService"
RUN export PYTHONPATH=/home/Appli_FoodGo/RestauService:$PYTHONPATH
# Installer les dépendances système nécessaires
RUN apt-get update && \
    apt-get install -y \
    libgirepository1.0-dev \
    gir1.2-gtk-3.0 \
    gcc g++ \
    libcairo2-dev \
    pkg-config \
    libdbus-1-dev \
    libsystemd-dev \
    && rm -rf /var/lib/apt/lists/*

# Installer pip et setuptools à jour
RUN pip install --upgrade pip setuptools

# Copier le fichier requirements.txt et installer les dépendances Python
COPY requirements.txt .
RUN pip install -r requirements.txt

# Copier le reste de l'application
COPY . .

# Expose the port used by the service
EXPOSE 5001

CMD ["python3", "app.py"]

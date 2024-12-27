#!/bin/bash

# Set PYTHONPATH (if required)
export PYTHONPATH=/home/Appli_FoodGo/RestauService:$PYTHONPATH

# Start services using Docker Compose
if docker-compose up -d --build; then
    echo "All services have been started successfully."
else
    echo "Failed to start services!"
    exit 1
fi

# Check service status
docker-compose ps
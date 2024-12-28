#!/bin/bash

# Set PYTHONPATH (if required)
export PYTHONPATH=/home/Appli_FoodGo/RestauService:$PYTHONPATH

# Ensure docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "docker-compose could not be found. Please install it first."
    exit 1
fi

# Start services using Docker Compose
if docker-compose up -d --build; then
    echo "All services have been started successfully."
else
    echo "Failed to start services! Displaying logs..."
    docker-compose logs
    exit 1
fi

# Check service status
echo "Service status:"
docker-compose ps

echo "To view logs, run: docker-compose logs"

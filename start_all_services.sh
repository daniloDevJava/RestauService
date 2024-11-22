#!/bin/bash

export PYTHONPATH=/home/Appli_FoodGo/RestauService:$PYTHONPATH

services=(
    "service_geolocalisation"
    "service_payment"
)

for service in "${services[@]}"; do
    if [ -d "$service" ]; then
        (
            cd "$service" || exit
            python3 app.py > "log_${service}.txt" 2>&1 &
        )
    else
        echo "Directory $service does not exist."
    fi
done
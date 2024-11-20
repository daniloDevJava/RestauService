#!/bin/bash

services=(
    "calcul_distance_service"
    "quartier_coordonnees_service"
    "get_location_service"
    "create_qr_service"
    "show_qr_service"
    "verification_qr_service"
)

for service in "${services[@]}"; do
    if [ -d "$service" ]; then
        (
            cd "$service" || exit
            python3 app.py > "log_${service}.txt" 2>&1
        )
    else
        echo "Directory $service does not exist."
    fi
done
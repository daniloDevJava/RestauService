function getLocation(endPoint) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            position => showPosition(position, endPoint), 
            showError
        );
    } else {
        console.log("La géolocalisation n'est pas supportée par ce navigateur.");
    }
}

function showPosition(position, endPoint) {
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    // Envoyer les données de localisation à ton serveur Flask
    fetch(endPoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            latitude: latitude,
            longitude: longitude
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Succès:', data);
    })
    .catch((error) => {
        console.error('Erreur:', error);
    });
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            console.log("L'utilisateur a refusé la demande de géolocalisation.");
            break;
        case error.POSITION_UNAVAILABLE:
            console.log("Les informations de localisation ne sont pas disponibles.");
            break;
        case error.TIMEOUT:
            console.log("La demande de localisation a expiré.");
            break;
        case error.UNKNOWN_ERROR:
            console.log("Une erreur inconnue s'est produite.");
            break;
    }
}

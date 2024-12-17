export function getLocation() {
    return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const latitude = position.coords.latitude;
                    const longitude = position.coords.longitude;
                    resolve({ latitude, longitude }); // Résoudre la promesse avec les coordonnées
                },
                (error) => {
                    showError(error);
                    reject(error); // Rejeter la promesse en cas d'erreur
                }
            );
        } else {
            alert("La géolocalisation n'est pas supportée par ce navigateur.");
            reject(new Error("Géolocalisation non supportée"));
        }
    });
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            alert("L'utilisateur a refusé la demande de géolocalisation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Les informations de localisation ne sont pas disponibles.");
            break;
        case error.TIMEOUT:
            alert("La demande de localisation a expiré.");
            break;
        case error.UNKNOWN_ERROR:
            alert("Une erreur inconnue s'est produite.");
            break;
    }
}

export const registerUser = async (userData) => {
  try {
    const response = await fetch(
      'http://localhost:6000/FOODGO-CONNEXIONETCOMPTES/foodGo-ConnexionEtComptes/user-management/signin/', // Vérifiez l'URL
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      }
    );

    
    if (!response.ok) {
      throw new Error(`Erreur du serveur: ${response.statusText}`);
    }

    const result = await response.json(); 
    return result; 
  } catch (error) {
    console.error('Erreur lors de l\'enregistrement:', error);
    throw error;
  }
};


export const loginUser = async (email, password) => {
  try {
    const response = await fetch(
      'http://localhost:6000/foodGo-ConnexionEtComptes/user-management/login', // Vérifiez l'URL
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }), 
      }
    );

    
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Échec de la connexion');
    }

    const result = await response.json();
    return result; 
  } catch (error) {
    console.error('Erreur lors de la connexion:', error.message);
    throw error;
  }
};
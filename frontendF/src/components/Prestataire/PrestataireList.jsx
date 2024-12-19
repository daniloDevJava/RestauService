import React from "react";
import Prestataire from "./Prestataire";
import prestataires from '../Assets/all_product';

function PrestatairesList({ activeCity }) {
  const cleanedActiveCity = activeCity && activeCity.trim().toLowerCase(); // Nettoyage de activeCity

  // Filtrer les prestataires en fonction de la ville active
  const filteredPrestataires =
    cleanedActiveCity === "all"
      ? prestataires
      : prestataires.filter((prestataire) => {
          const city = prestataire.city.trim().toLowerCase();
          return city === cleanedActiveCity;
        });

  // Ne pas afficher le composant si aucun prestataire n'est trouvé
  if (filteredPrestataires.length === 0) {
    return null; // Ne retourne rien si la liste est vide
  }

  return (
    <div className="prestataires-list">
      {filteredPrestataires.map((prestataire) => (
        <Prestataire
          key={prestataire.id}
          restoName={prestataire.restoName}
          description={prestataire.description}
          prestataireName={prestataire.prestataireName}
          prestataireRole={prestataire.prestataireRole}
          city={prestataire.city}
          image={prestataire.image}
          productImage={prestataire.productImage}
          price={prestataire.price}
        />
      ))}
    </div>
  );
}

export default PrestatairesList;

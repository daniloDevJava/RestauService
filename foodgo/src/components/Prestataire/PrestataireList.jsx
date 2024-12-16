import React from "react";
import Prestataire from "./Prestataire";
import prestataires from "../Assets/all_product"; // Exemple des données centralisées
import "./Prestataire.css";

function PrestatairesList({activeCity}) {

  const cleanedActiveCity = activeCity && activeCity.trim().toLowerCase();  // Vérifier si activeCity existe
  // Filtrer les prestataires en fonction de la ville active
  const filteredPrestataires =
  cleanedActiveCity === "all"
    ? prestataires
    : prestataires.filter((prestataire) => {
      const city = prestataire.city.trim().toLowerCase();  // Nettoyer et rendre en minuscules
          return city === cleanedActiveCity;
      });

  return (
    <div className="prestataires-list">
      { filteredPrestataires.length > 0 ? (
        filteredPrestataires.map((prestataire) => (
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
      ))
    ) : (
      <p >Aucun prestataire trouvé pour cette ville.</p>
    )}
    </div>
  );
}

export default PrestatairesList;

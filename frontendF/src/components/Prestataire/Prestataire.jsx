import React from "react";
import "./Prestataire.css"; // Import de styles personnalisés

function Prestataire({ restoName, description, prestataireName, prestataireRole, city, image, productImage, price }) {
  return (
    <div className="prestataire-container">
      <div className="food-container" data-city={city.toLowerCase()}>
        {/* Informations principales */}
        <div className="style">
          <div className="resto-name1">{restoName}</div>
          <div className="resto-description">
            {description.split("<br/>").map((line, index) => (
              <React.Fragment key={index}>
                {line}
                <br />
              </React.Fragment>
            ))}
          </div>
          <img src={image} alt={prestataireName} className="prestataire-image" />
          <div className="prest-name">{prestataireName}</div>
          <div className="prest-role">{prestataireRole}</div>
          <div className="plus">
            <div className="btn-plus">
              <button className="add-btn"><a href="/login" >+</a></button>
            </div>
          </div>
        </div>

        {/* Image du produit */}
        <div className="image">
          <img src={productImage} alt="Produit" />
        </div>

        {/* Prix et localisation */}
        <div className="price">
          <p>{price}</p>
        </div>
        <div className="localisation">
          <div className="local-name">{city}</div>
        </div>
      </div>
    </div>
  );
}

export default Prestataire;

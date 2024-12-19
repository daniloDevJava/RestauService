import React from 'react';
import '../Pages/CSS/infoPage.css'
import { Link } from 'react-router-dom';
import Footer from '../../components/Footer/Footer';

const InfoPage = () => {
  return (
    <div>
      <header>
        <div className="logo">
          <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
            <p>FOODGO</p>
          </Link>
        </div>
        <nav className='Menu'>
          <ul>
            <li >
              <Link style={{ textDecoration: 'none'}} to='/'>Home</Link>
            </li>
            <li >
              <Link style={{ textDecoration: 'none'}} >Food</Link>
            </li>
            <li>
              <Link style={{ textDecoration: 'none'}}to='/Offers'>Offers</Link>
            </li>
            <li>
              <Link style={{ textDecoration: 'none'}} to='/Services'>Services</Link>
            </li>
            <li>
              <Link style={{ textDecoration: 'none'}} to='/Contact'>Contact-us</Link>
            </li>
          </ul>
        </nav>
      </header>
      <div id="about">
        <h2>À Propos</h2>
        <p>Nous sommes un groupe d'etudiants de l'université de yaounde un </p>
      </div>
      <div id="portfolio">
        <h2>Portfolio</h2>
        <p>Découvrez nos projets récents.</p>
      </div>
      <div id="page">
        <h2>Page</h2>
        <p>Détails sur nos services.</p>
      </div>
      <div id="faq">
        <h2>FAQ</h2>
        <p>Questions fréquemment posées.</p>
      </div>
      <div id="reviews">
        <h2>Avis</h2>
        <p>Lisez les commentaires de nos clients.</p>
      </div>
      <div id="contact-us">
        <h2>Contactez-Nous</h2>
        <p>Informations de contact.</p>
      </div>
      <div id="privacy-policy">
        <h2>Politique de Confidentialité</h2>
        <p>Détails sur notre politique de confidentialité.</p>
      </div>
      <div id="terms-of-use">
        <h2>Conditions d'Utilisation</h2>
        <p>Conditions d'utilisation de nos services.</p>
      </div>
      <div id="buy-and-sell">
        <h2>Acheter et Vendre</h2>
        <p>Informations sur l'achat et la vente.</p>
      </div>
      <div id="reviews">
        <h2>Avis</h2>
        <p>Avis supplémentaires de nos clients.</p>
      </div>
      <footer>
        <Footer/>
      </footer>
    </div>
  );
};

export default InfoPage;
import React, { useState } from 'react';
import Navbar from '../Navbar/Navbar';
import arrow from '../Assets/Play.svg'; 
import PrestatairesList from '../Prestataire/PrestataireList';
import '../Navbar/Navbar.css';
import './Entete.css';
import search from '../Assets/search.svg';

const Entete = () => {
    const [activeCity, setActiveCity] = useState("all"); // "all" pour afficher tout par défaut
    const [searchTerm, setSearchTerm] = useState(""); // État pour la recherche

    // Fonction pour mettre à jour la ville active
    const handleFilterClick = (city) => {
        setActiveCity(city);
    };

    // Fonction pour gérer la recherche
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        // Vous pouvez ajouter ici la logique pour gérer la recherche
        console.log("Recherche:", searchTerm);
    };

    return (
        <div>
            <div className="Entete">
                <div className="navbar">
                    <Navbar />
                </div>
                <section className="entete">
                    <div className="texte">
                        <p>All over the country!</p>
                        <h1>Most Fasted Food Delivery Service</h1>
                        <h2>Get the best quality and most delicious food in the world</h2>
                    </div>
                    <div className="Next">
                        <button className="order">
                            <a href="/login">Order Food</a>
                        </button>
                        <div className="view">
                            <img src={arrow} alt="" />
                            <a href="/#middle">View Menu</a>
                        </div>
                    </div>
                </section>
            </div>

            <div className="zone-de-reccherche">
                        <form onSubmit={handleSearchSubmit} className="search-form">
                            <input
                                type="text"
                                value={searchTerm}
                                onChange={handleSearchChange}
                                placeholder="Rechercher..."
                                className="search-input"
                            />
                            <button type="submit" className="search-button">
                                <img src={search} alt="Search"  />
                            </button>
                        </form>
                    </div>


            <section className="main">
                <div className="city-container">
                    <div
                        className={`filter ${activeCity === "all" ? "city-all active" : ""}`}
                        onClick={() => handleFilterClick("all")}
                    >
                        <p>All</p>
                    </div>

                    <ul className="city-names">
                        {["yaounde", "douala", "baffoussam", "buea", "ngaoundere", "bertoua", "adamaoua"].map(
                            (city) => (
                                <li
                                    key={city}
                                    className={`filter ${activeCity === city ? "active" : ""}`}
                                    onClick={() => handleFilterClick(city)}
                                    data-filter={city}
                                >
                                    {city.charAt(0).toUpperCase() + city.slice(1)}
                                </li>
                            )
                        )}
                    </ul>
                </div>

                <PrestatairesList activeCity={activeCity} />
            </section>
        </div>
    );
};

export default Entete;
import React,{useState} from "react";
import './Accueil.css';
import './Main.css';
import elle from '../Assets/elle.jpeg';
import viande from '../Assets/viande.png'

const Accueil = () => {

    const [menu, setMenu] = useState("home");
    const [activeCity, setActiveCity] = useState("all"); // "all" pour afficher tout par défaut

    // Fonction pour mettre à jour la ville active
    const handleFilterClick = (city) => {
        setActiveCity(city);
    };

    return (
        <>
            <section className="home">
                <div className="home-content">
                    <h3>All over the world!</h3>
                    <h1>Most Fasted Food <br/>Delivery Service</h1>
                    <p>Get the best quality and most delicious food <br/>in the world
                    you can have them all on our web <br/>application</p>

                    <div className="btn-box">
                        <li onClick={()=>{setMenu("foods")}}>Order Food{menu==="foods"}</li>
                        <li onClick={()=>{setMenu("services")}}>View Menu{menu==="services"}</li>
                    </div>
                </div>
                <span className="home-imgHover"></span>
            </section>

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
                <div className="prestataire-container">
                    <div className="food-container" data-city="yaounde">
                        <div className="style">
                            <div className="resto-name1">Restaurant Akua</div>
                            <div className="resto-description">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <img src={elle} alt="" />
                            <div className="prest-name">Halim Maïmouna</div>
                            <div className="prest-role">Prestataire</div>
                            <div className="plus">
                                <div className="btn-plus"><button class="add-btn">+</button></div>
                            </div>
                        </div>
                        <div className="image">
                            <img src={viande} alt="" />
                        </div>
                        <div className="price"><p>5.00</p></div>
                        <div className="localisation">
                            <div className="local-name">Yaoundé</div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default Accueil
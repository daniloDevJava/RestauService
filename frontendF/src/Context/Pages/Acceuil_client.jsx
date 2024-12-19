import React from 'react';
//import { useNavigate } from 'react-router-dom'; 
import { useCart } from '../../components/cartcontext/cartcontext';
import Navbar from '../../components/Navbar/Navbar';
import "../Pages/CSS/Acceuil_client.css";
import girlImg from "../../components/Assets/young.png";
import searchIcon from "../../components/Assets/search.svg";
import categoryImg1 from "../../components/Assets/background.svg";
import categoryImg2 from "../../components/Assets/background.svg";
import categoryImg3 from "../../components/Assets/background.svg";
import foodImg1 from "../../components/Assets/viande.png";
import foodImg2 from "../../components/Assets/viande.png";
import foodImg3 from "../../components/Assets/viande.png";
import Footer from "../../components/Footer/Footer";

const AcceuilClient = ({ userName="Jessica" }) => {
    //const navigate = useNavigate(); 

    const categories = [
        { img: categoryImg1, name: "Salades" },
        { img: categoryImg2, name: "Pizzas" },
        { img: categoryImg3, name: "Desserts" },
        { img: categoryImg1, name: "Plats traditionnels" },
        { img: categoryImg2, name: "Fritures" },
        { img: categoryImg3, name: "Boissons" },
    ];

    const foods = [
        { img: foodImg1, name: "Boeuf Grillé", restaurant: "Restaurant D", price: "15€" },
        { img: foodImg2, name: "Poulet Frit", restaurant: "Restaurant E", price: "10€" },
        { img: foodImg3, name: "Poisson", restaurant: "Restaurant F", price: "12€" },
        { img: foodImg1, name: "Boeuf Grillé", restaurant: "Restaurant D", price: "15€" },
        { img: foodImg2, name: "Poulet Frit", restaurant: "Restaurant E", price: "10€" },
        { img: foodImg3, name: "Poisson", restaurant: "Restaurant F", price: "12€" },
    ];
    const { addToCart } = useCart(); 

   // const goToCart = () => {
     //   navigate('/cart'); 
    //};
    return (
        <div className='Acceuil-client'>
            <div className="Navbar-client">
                <Navbar />
            </div>
            <section className='up'>
                <div className="texte">
                    <h1>Trouvez tous types de repas et faites vous livrer en un temps record</h1>
                    <h2>Recherchez des repas pour des restaurants proches de chez vous</h2>
                </div>
                <div className="User-picture">
                    <img src={girlImg} alt="une fille" />
                </div>
            </section>
            <section className='introduction'>
                <div className="hello-text">Hello {userName}</div>
                <div className="barre-recherche">
                    <img src={searchIcon} alt="Icone de recherche" />
                    <input type="text" placeholder="Rechercher un restaurant" />
                </div>
            </section>
            <section className='categories'>
                <h2>Catégories</h2>
                <div className="grid-container">
                    {categories.map((item, index) => (
                        <div className="card" key={index}>
                            <div className="category-image">
                                <img src={item.img} alt={`Catégorie ${item.name}`} />
                                <div className="overlay">
                                    <h3>{item.name}</h3>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
            <section className='nourriture'>
                <h2>Nourriture</h2>
                <div className="grid-container">
                    {foods.map((item, index) => (
                        <div className="card" key={index}>
                            <img src={item.img} alt={`Nourriture ${item.name}`} />
                            <div className="card-content">
                                <div className="info">
                                    <h3>{item.name}</h3>
                                    <p>{item.restaurant}</p>
                                    <p>{item.price}</p>
                                </div>
                               
                                <button className="order-button" onClick={() => addToCart(item)}>+</button> {/* Passer l'article au panier */}
                            </div>
                        </div>
                    ))}
                </div>
            </section>
            <Footer />
        </div>
    );
};

export default AcceuilClient;
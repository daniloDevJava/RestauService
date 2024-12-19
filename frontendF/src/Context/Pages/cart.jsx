import React from 'react';
import { useCart } from '../../components/cartcontext/cartcontext'; 
import './CSS/Cart.css'; 
import trashIcon from '../../components/Assets/icons8-trash.svg'; 
import Navbar from '../../components/Navbar/Navbar';

const Cart = () => {
    const { cartItems, removeFromCart, totalAmount } = useCart();

    return (

        <div className="cart">
            <div>
                <Navbar/>
            </div>
            <div className="cart-container">
                <h1>Votre Panier</h1>
                {cartItems.length === 0 ? (
                    <div className="empty-cart">
                        <p>Votre panier est vide.</p>
                    </div>
                ) : (
                    <div className="cart-items">
                        {cartItems.map((item, index) => (
                            <div className="cart-item" key={index}>
                                <img src={item.img} alt={`Article ${item.name}`} className="cart-item-image" />
                                <div className="cart-item-details">
                                    <h3>{item.name}</h3>
                                    <p>{item.restaurant}</p>
                                    <p>{item.price} €</p>
                                </div>
                                <button 
                                    className="remove-item-button" 
                                    onClick={() => removeFromCart(index)}
                                >
                                    <img src={trashIcon} alt="Supprimer" width="50px" height="50px"/>
                                </button>
                            </div>
                        ))}
                    </div>
                )}
                <div className="cart-summary">
                    <h2>Total: {totalAmount} €</h2>
                    <div className="cart-actions">
                        <button className="checkout-button">Passer à la caisse</button>
                        <a href="/Food"><button className="continue-shopping-button">Continuer vos achats</button></a>
                    </div>
                </div>
            </div>
            
        </div>
    );
};

export default Cart;
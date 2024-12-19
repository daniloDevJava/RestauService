
import React, { createContext, useState, useContext } from 'react';

const CartContext = createContext();

export const useCart = () => {
    return useContext(CartContext);
};

export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);

    const addToCart = (item) => {
        setCartItems((prevItems) => [...prevItems, item]);
    };

    const removeFromCart = (index) => {
        setCartItems((prevItems) => prevItems.filter((_, i) => i !== index));
    };

    const totalAmount = cartItems.reduce((total, item) => total + parseFloat(item.price), 0).toFixed(2);

    return (
        <CartContext.Provider value={{ cartItems, addToCart, removeFromCart, totalAmount }}>
            {children}
        </CartContext.Provider>
    );
};
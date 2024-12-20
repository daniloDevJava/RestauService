import React, { useState } from 'react';
import './Add_product.css'
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';

const AddProduct = () => {
  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [ratings, setRatings] = useState({});
  const [newProduct, setNewProduct] = useState({
    name: '',
    image: null,
    quantity: 0,
    price: 0,
  });

  // Handle new product addition
  const handleAddProduct = () => {
    setProducts([...products, { ...newProduct, id: Date.now() }]);
    setNewProduct({ name: '', image: null, quantity: 0, price: 0 });
  };

  // Handle order placement
  const handleOrder = (productId, quantity) => {
    const updatedProducts = products.map((product) =>
      product.id === productId
        ? { ...product, quantity: product.quantity - quantity }
        : product
    );
    setProducts(updatedProducts);
    setOrders([
      ...orders,
      { id: Date.now(), productId, quantity, status: 'En cours' },
    ]);
  };

  // Handle order completion
  const completeOrder = (orderId) => {
    const updatedOrders = orders.map((order) =>
      order.id === orderId ? { ...order, status: 'Terminé' } : order
    );
    setOrders(updatedOrders);
  };

  // Handle product rating
  const handleRating = (productId, rating) => {
    const updatedRatings = {
      ...ratings,
      [productId]: (ratings[productId] || []).concat(rating),
    };
    setRatings(updatedRatings);
  };

  return (
    
    <div style={{ padding: '20px' }} className='product-management'>
        <div className="Navbar">
            <Navbar/>
        </div>
      <h1>Interface Prestataires et Commandes</h1>

      <section>
        <h2>Ajouter un produit</h2>
        <input
          type="text"
          placeholder="Nom de la nourriture"
          value={newProduct.name}
          onChange={(e) =>
            setNewProduct({ ...newProduct, name: e.target.value })
          }
        />
        <input
          type="file"
          placeholder="Image de la nourriture"
          onChange={(e) =>
            setNewProduct({ ...newProduct, image: e.target.files[0] })
          }
        />
        <label htmlFor="Quantité en stock">Quantité en stock</label>
        <input
          type="number"
          placeholder="Quantité en stock"
          value={newProduct.quantity}
          onChange={(e) =>
            setNewProduct({ ...newProduct, quantity: Number(e.target.value) })
          }
        />
        <label htmlFor="Prix">Prix</label>
        <input
          type="number"
          placeholder="Prix"
          value={newProduct.price}
          onChange={(e) =>
            setNewProduct({ ...newProduct, price: Number(e.target.value) })
          }
        />
        <button onClick={handleAddProduct}>Ajouter</button>
      </section>

      <section>
        <h2>Produits disponibles</h2>
        {products.map((product) => (
          <div key={product.id} style={{ marginBottom: '10px' }}>
            <h3>{product.name}</h3>
            {product.image && (
              <img
                src={URL.createObjectURL(product.image)}
                alt={product.name}
                style={{ width: '100px' }}
              />
            )}
            <p>Quantité: {product.quantity}</p>
            <p>Prix: {product.price}€</p>
            <button onClick={() => handleOrder(product.id, 1)}>
              Commander
            </button>
          </div>
        ))}
      </section>

      <section>
        <h2>Commandes</h2>
        {orders.map((order) => (
          <div key={order.id} style={{ marginBottom: '10px' }}>
            <p>
              Produit: {products.find((p) => p.id === order.productId)?.name}
            </p>
            <p>Quantité: {order.quantity}</p>
            <p>Status: {order.status}</p>
            {order.status === 'En cours' && (
              <button onClick={() => completeOrder(order.id)}>
                Terminer la commande
              </button>
            )}
          </div>
        ))}
      </section>

      <section>
        <h2>Noter un prestataire</h2>
        {products.map((product) => (
          <div key={product.id} style={{ marginBottom: '10px' }}>
            <h3>{product.name}</h3>
            {[1, 2, 3, 4, 5].map((star) => (
              <button
                key={star}
                onClick={() => handleRating(product.id, star)}
              >
                {star}★
              </button>
            ))}
            <p>
              Moyenne: 
              {(
                (ratings[product.id]?.reduce((a, b) => a + b, 0) || 0) /
                (ratings[product.id]?.length || 1)
              ).toFixed(1)}
            </p>
          </div>
        ))}
      </section>
      <div className="Footer">
        <Footer/>
      </div>
    </div>
  );
};

export default AddProduct;

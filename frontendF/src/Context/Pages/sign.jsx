import React, { useState } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import SignClient from './signclient'; 
import SignVendeur from './signvendeur'; 
import '../Pages/CSS/sign.css';

const Sign = () => {
  const [role, setRole] = useState('');

  const RoleSelection = () => (
    <div className='conteneur'>
      <h1>Choisissez votre rôle</h1>
      <div className="role-options">
        <div 
          className={`role-card ${role === 'client' ? 'selected' : ''}`} 
          onClick={() => setRole('client')}
        >
          <h3>Client</h3>
          <p>Je veux acheter des produits</p>
        </div>
        <div 
          className={`role-card ${role === 'vendeur' ? 'selected' : ''}`}
          onClick={() => setRole('vendeur')}
        >
          <h3>Vendeur</h3>
          <p>Je propose des plats</p>
        </div>
      </div>
    </div>
  );

  return (
    <div className='Sign'>
      <div className='Navbar'>
        <Navbar />
      </div>
      
      {role === '' ? (
        <RoleSelection /> 
      ) : role === 'client' ? (
        <SignClient setRole={setRole} /> 
      ) : (
        <SignVendeur setRole={setRole} /> 
      )}
    </div>
  );
};

export default Sign;
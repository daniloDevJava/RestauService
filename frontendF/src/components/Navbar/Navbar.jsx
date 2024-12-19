import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';
import cartImage from "../Assets/cart_icon.png"
import profileImage from "../Assets/elle.jpeg"

const Navbar = () => {
  const location = useLocation();
  const isHomePage = location.pathname === '/';
  const isFoodPage = location.pathname === '/Food'|| location.pathname === '/cart'
   

  return (
    <div className='header'>
      <div className={`navbar ${isHomePage ? '' : 'active'}`}>
        <div className={`logo ${location.pathname === '/' ? '' : 'pink'}`}>
          <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
            <p>FOODGO</p>
          </Link>
        </div>
        <nav className='Menu'>
          <ul>
            <li className={`short ${location.pathname === '/' ? 'active' : ''}`}>
              <Link style={{ textDecoration: 'none', color: isFoodPage ? 'black' : 'white'}} to='/'>Home</Link>
            </li>
            <li className={`short ${isFoodPage ? 'active' : ''}`}>
              <Link style={{ textDecoration: 'none', color: isFoodPage ? 'red' : 'white' }} to='/Food'>Food</Link>
            </li>
            <li className={`short ${location.pathname === '/Offers' ? 'active' : ''}`}>
              <Link style={{ textDecoration: 'none', color: isFoodPage ? 'black' : 'white'}} to='/Offers'>Offers</Link>
            </li>
            <li className={`medium ${location.pathname === '/Services' ? 'active' : ''}`}>
              <Link style={{ textDecoration: 'none', color: isFoodPage ? 'black' : 'white'}} to='/Services'>Services</Link>
            </li>
            <li className={`long ${location.pathname === '/Contact' ? 'active' : ''}`}>
              <Link style={{ textDecoration: 'none', color: isFoodPage ? 'black' : 'white'}} to='/Contact'>Contact-us</Link>
            </li>
          </ul>
        </nav>
        <div className="connexion">
            {isFoodPage ? (
                <div className="icon"> 
                <Link to="/cart" className="cart-icon">
                    <img src={cartImage} alt="Shopping Cart" />
                </Link>
                <Link to="/profile" className="profile-icon">
                    <img src={profileImage} alt="User Profile" />
                </Link>
                </div> 
            ) : (
            <>
            <button className={`log ${location.pathname === '/login' ? 'active' : ''}`}>
              <a href="/login" style={{ textDecoration: 'none', color: 'inherit' }}>
                Log in
              </a>
            </button>
            <button
              className={`sign ${location.pathname === '/sign' ? 'active' : ''}`}
            >
              <a href="/sign" style={{ textDecoration: 'none', color: 'inherit' }}>
                Sign in
              </a>
            </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default Navbar;
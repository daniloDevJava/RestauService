import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'
import logo from '../Assets/logo.png'
import cart_icon from '../Assets/cart_icon.png'

const Navbar = () => {

    const [menu, setMenu] = useState("home");

    return (
            <div className='navbar'>
                <div className="nav-logo">
                    <img src={logo} alt="" />
                </div>
                <ul className="nav-menu">
                    <li onClick={()=>{setMenu("home")}}><Link style={{ textDecoration: 'none' }} to='/'>Home</Link>{menu==="home"?<hr/>:<></>}</li>
                    <li onClick={()=>{setMenu("foods")}}>Foods{menu==="foods"?<hr/>:<></>}</li>
                    <li onClick={()=>{setMenu("offers")}}>Offers{menu==="offers"?<hr/>:<></>}</li>
                    <li onClick={()=>{setMenu("services")}}>Services{menu==="services"?<hr/>:<></>}</li>
                    <li onClick={()=>{setMenu("contact")}}><Link style={{ textDecoration: 'none'}}>Contact Us</Link>{menu==="contact"?<hr/>:<></>}</li>
                </ul>
                <div className="nav-login-cart">
                   <Link to='/login'><button>Log in</button></Link>
                   
                   <Link to='/cart'><img src={cart_icon} alt="" /></Link>
                    <div className="nav-cart-count">0</div>
                </div>
            </div>
    )
}

export default Navbar
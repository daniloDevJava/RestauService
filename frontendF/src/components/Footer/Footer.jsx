import React from 'react';
import './Footer.css';
import facebookIcon from '../Assets/facebook.svg';
import instagramIcon from '../Assets/instagram.svg';
import twitterIcon from '../Assets/twitter-alt-circle.svg';
import youtubeIcon from '../Assets/youtube.svg';

const Footer = () => {
  return (
    <div className='footer'>
      <div className="footer-container">
        <div className="footer-section">
          <h3>Delicious</h3>
          <p>We renew in food and restaurants to present good services.</p>
        </div>
        <div className="footer-section">
          <h4>Company</h4>
          <ul>
            <li><a href="/information#about" style={{ textDecoration: 'none', color: '#000' }}>About us</a></li>
            <li><a href="/information#portfolio" style={{ textDecoration: 'none', color: '#000' }}>Portfolio</a></li>
            <li><a href="/information#page" style={{ textDecoration: 'none', color: '#000' }}>Page</a></li>
            <li><a href="/information#faq" style={{ textDecoration: 'none', color: '#000' }}>FAQ</a></li>
            <li><a href="/information#reviews" style={{ textDecoration: 'none', color: '#000' }}>Reviews</a></li>
          </ul>
        </div>
        <div className="footer-section">
          <h4>Support</h4>
          <ul>
            <li><a href="/information#contact" style={{ textDecoration: 'none', color: '#000' }}>Contact Us</a></li>
            <li><a href="/information#privacy" style={{ textDecoration: 'none', color: '#000' }}>Privacy Policy</a></li>
            <li><a href="/information#terms" style={{ textDecoration: 'none', color: '#000' }}>Terms of Use</a></li>
            <li><a href="/information#buy-sell" style={{ textDecoration: 'none', color: '#000' }}>Buy & Sell</a></li>
            <li><a href="/information#reviews" style={{ textDecoration: 'none', color: '#000' }}>Reviews</a></li>
          </ul>
        </div>
        <div className="footer-section">
          <h4>Contact</h4>
          <ul>
            <li>Cairo, Egypt</li>
            <li>+021006458793</li>
            <li>Cairo21@gmail.com</li>
            <li><a href="/information#buy-sell" style={{ textDecoration: 'none', color: '#000' }}>Buy & Sell</a></li>
            <li><a href="/information#reviews" style={{ textDecoration: 'none', color: '#000' }}>Reviews</a></li>
          </ul>
        </div>
      </div>
      <hr />
      <div className="footer-bottom">
        <p><a href="/information#terms" style={{ textDecoration: 'none', color: '#000' }}>Terms of Services</a></p>
        <p><a href="/information#privacy" style={{ textDecoration: 'none', color: '#000' }}>Privacy Policy</a></p>
        <div className="social-icons">
          <a href="https://facebook.com">
            <img src={facebookIcon} alt="Facebook" />
          </a>
          <a href="https://instagram.com">
            <img src={instagramIcon} alt="Instagram" />
          </a>
          <a href="https://twitter.com">
            <img src={twitterIcon} alt="Twitter" />
          </a>
          <a href="https://youtube.com">
            <img src={youtubeIcon} alt="YouTube" />
          </a>
        </div>
      </div>
    </div>
  );
}

export default Footer;
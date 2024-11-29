import React from 'react'
import './Footer.css'
/*import facebookIcon from '../Assets/facebook.jpeg'
import instagramIcon from '../Assets/instagram.jpeg'
import twitterIcon from '../Assets/twitter.png'
import youtubeIcon from '../Assets/youtube.png'
*/

const Footer = () => {
  return (
    <div className='footer'>
        <div className="footer-container">
            <div className="footer-section">
                <h3>Delicious</h3>
                <p>We renew in food and restaurants to present good services.</p>
            </div>
            <div className="footer-section">
                <h4>Compagny</h4>
                <ul>
                    <li>About us</li>
                    <li>Portfolio</li>
                    <li>Page</li>
                    <li>FAQ</li>
                    <li>Reviews</li>
                </ul>
            </div>
            <div className="footer-section">
                <h4>Support</h4>
                <ul>
                    <li>Contact Us</li>
                    <li>Privacy Policy</li>
                    <li>Terms of Use</li>
                    <li>Buy & Sell</li>
                    <li>Reviews</li>
                </ul>
            </div>
            <div className="footer-section">
                <h4>Contact</h4>
                <ul>
                    <li>Cairo, Egypt</li>
                    <li>+021006458793</li>
                    <li>Cairo21@gmail.com</li>
                    <li>Buy & Sell</li>
                    <li>Reviews</li>
                </ul>
            </div>
        </div>
        <hr />
        <div className="footer-bottom">
            <p>Terms of Services</p>
            <p>Privacy Policy</p>
            <div className="social-icons">
                {/*<a href="https://facebook.com">
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
                </a>*/}
            </div>
        </div>
    </div>
  )
}

export default Footer
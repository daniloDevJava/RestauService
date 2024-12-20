import React from 'react'
import { Link } from 'react-router-dom'
import './style.css';
import './Style1.css';
import './Style2.css';
import viande from '../Assets/viande.png'
import hella from '../Assets/hella.jpg'


const Dash3 = () => {
  return (
    <div className='global'>
      <div className='main-container'>
        <div className="header">
          Hello Patricia
        </div>
        <div className="sidebar">
            <div className="elt1"> <span className='elt2'>FoodGo </span> <span className='elt3'>.</span></div>
              <div className="sidebar-content">
                <div className="menu-item">
                  <div className="icon favorite-icon"></div>
                    <Link style={{ textDecoration: 'none' }} to="/Dashboard"> <span >Dashboard</span></Link>
                  </div>
                  <div className="menu-item ">
                    <div className="icon setting-icon"></div>
                      <Link style={{ textDecoration: 'none' }} to="/Dash4"> <span >Clients</span></Link>
                    </div>      
                    <div className="menu-item active">
                      <div className="icon dashboard-icon"></div>
                        <Link style={{ textDecoration: 'none' }} to="/Dash3"> <span className='elt67'>Restaurants</span></Link>
                      </div>
                    </div>
                      <div className="upgrade">
                          <div className="upgrade-content">
                              <h3>Upgrade Your <br />Account to Get Free Voucher</h3>
                              <button className="upgrade-button">Upgrade</button>
                          </div>
                      </div>
        </div>
            
      </div>

      <div className="section_search">
        <div className="title">Popular Dishes</div>
        <div className="list">
          <div className="elt76">
            <img className="elt77" src={viande} alt='viande'/>
            <div className="elt78">This is one of our best dishes, you
            can<br/> taste many delicacies.</div>
            <div className="elt79">
              <img className="elt80" src={hella} alt='hella' />
              <div className="elt81">Prestataire</div>
              <div className="elt82" style={{top: '200px'}}>Halim Maïmouna</div>
            </div>
            <div className="elt83">
              <div className="elt85">Yaoundé</div>
            </div>
            <div className="plus" style={{top: '200px'}}>
              <div className="plus_button"></div>
              <div className="plus_button"></div>
              <div className="plus_button"></div>
              <div className="plus_elt">
                <div>Desactiver/Reactiver</div>
                <div>Performances</div>
              </div>
            </div>
            <div className="elt85">
              <div className="elt86">
                <div className="elt87">
                  <div className="elt88"></div>
                </div>
                <div className="elt89">
                  <div className="elt90"></div>
                </div>
              </div>
              <div className="elt91">5.00</div>
            </div>
            <div className="elt92">Restaurant Akua</div>
          </div>
        </div>
      </div>

    </div>
  )
}

export default Dash3

import React from 'react'
import { Link } from 'react-router-dom';
import './style.css';
import './Style1.css';
import './Style2.css';
import profile2 from '../Assets/profile2.jpeg'
import profile1 from '../Assets/elle.jpeg'
import hello from '../Assets/hello.jpeg'

const Dash4 = () => {
  return (
    <div className='global'>
      <div className='main-container'>
                  <div className="header">
                      Hello Patricia
                  </div>
                  <div className="sidebar">
                      <div className="elt1"> <span className='elt2'>FoodGo </span> <span className='elt3'>.</span></div>
                      <div className="sidebar-content">
                          <div className="menu-item ">
                              <div className="icon favorite-icon"></div>
                              <Link style={{ textDecoration: 'none' }} to="/Dashboard"> <span>Dashboard</span></Link>
                          </div>
                          <div className="menu-item active">
                              <div className="icon dashboard-icon"></div>
                              <Link style={{ textDecoration: 'none' }} to="/Dash4"> <span className='elt67'>Clients</span></Link>
                          </div>
                          <div className="menu-item ">
                              <div className="icon setting-icon"></div>
                              <Link style={{ textDecoration: 'none' }} to="/Dash3"> <span>Restaurants</span></Link>
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

      <div className="main_content" style={{top: '200px'}} >
        <div className="elt">
          <div className="section_search">
            
            <div className="list">
              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>

              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>

              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>

              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>

              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>

              <div className="elt_list1 elt68">
                <img className="elt69" src={profile2} alt='profile2' />
                <div className="elt70">
                  <img className="elt71" src={profile1} alt='elle'/>
                  <div className="elt72">(+237) 6 95 98 79 87</div>
                  <div className="elt73">Halim Maïmouna</div>
                </div>
                <div className="plus">
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_button"></div>
                  <div className="plus_elt">
                    <div>12/02/2024</div>
                     {/*<div>Performances</div> */}
                  </div>
                </div>
                <div className="elt75">
                  <div className="elt75">Yaoundé</div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>

      {/* Selection for search */} 
      <div class="search-bar">
        <div class="search-icon">
            <div class="search-circle"></div>
        </div>
        <input class="search-placeholder" type="text" placeholder="What do you want eat today..."/>
      </div>

      <img class="elt41" src={hello} alt='hello' />

    </div>
  )
}

export default Dash4

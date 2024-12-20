import React from 'react';
import { Link } from 'react-router-dom';
import './style.css';
import './Style1.css';
import './Style2.css';
import viande from '../Assets/viande.png'
import hella from '../Assets/hella.jpg'
import force from '../Assets/force.jpg'
import hello from '../Assets/hello.jpeg'



const Dash2 = () => {
  return (
    <div className='global'>
        <div className='main-container'>
            <div className="header">
                Hello Patricia
            </div>
            <div className="sidebar">
                <div className="elt1"> <span className='elt2'>FoodGo </span> <span className='elt3'>.</span></div>
                <div className="sidebar-content">
                    <div className="menu-item active">
                        <div className="icon dashboard-icon"></div>
                        <Link style={{ textDecoration: 'none' }} to="/Dashboard"> <span className='elt67'>Dashboard</span></Link>
                    </div>
                    <div className="menu-item ">
                        <div className="icon favorite-icon"></div>
                        <Link style={{ textDecoration: 'none' }} to="/Dash4"> <span >Clients</span></Link>
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

        <div className='main_main'>
            <div className="main_second elt4">
                <div className="img elt5">
                    <div className="elt6">
                        <div className="elt7">Total Client</div>
                        <div className="elt8">300k</div>
                    </div>
                    <div className="elt9">
                        <div className="elt7">Total restaurant</div>
                        <div className="elt8">1k</div>
                    </div>
                    <div className="elt11" >
                        <div className="elt7">Total comandes</div>
                        <div className="elt8">20k</div>
                    </div>
                    <div className="elt12">
                        <div className="elt7">Commande en cours</div>
                        <div className="elt8">25k</div>
                    </div>
                    <div className="elt13">
                        <div className="elt7">Commandes livrées</div>
                        <div className="elt8">25k</div>
                    </div>
                    <div className="elt14">
                        <div className="elt7">Commandes annulées</div>
                        <div className="elt8">20k</div>
                    </div>
                    <img className="elt10" alt='force' src={force} />
                </div>
            </div>

            <div className="main_top">
                <div className="elt15">
                    <div className="elt16">
                        <div className="elt17">
                            <div className="elt18"></div>
                            <div className="elt19"></div>
                            <div className="elt20"></div>
                            <div className="elt21"></div>
                        </div>
                        <div className="elt22">
                            <div className="elt23"></div>
                            <div className="elt24">
                                <div className="elt25"></div>
                                <div className="elt26"></div>
                            </div>
                        </div>
                        <div className="elt27">
                            <div className="elt24"></div>
                            <div className="elt24">
                                <div className="elt25"></div>
                                <div className="elt28"></div>
                            </div>
                        </div>
                        <div className="elt29">Top Up</div>
                        <div className="elt30">Transfer</div>
                        <div className="elt31">
                            <div className="elt32">Balance</div>
                            <div className="elt33">$12.000</div>
                        </div>
                    </div>
                    <div className="elt34">Regular scale</div>
            </div>

            <div className="main_comment">
                    <div className="elt35">
                        <div className="elt36">Alice Kaimoise</div>
                            <div className="elt37">
                                <div className="elt38">Yaoundé, rue 23</div>
                                <div className="elt39">
                                    <div className="elt40"></div>
                                </div>
                            </div>
                        
                        {/*Selection for element de restaurant */}
                        <div className="elt42">
                            <div className="elt43">Change</div>
                            <div className="elt44"></div>
                        </div>
                        <div className="elt45">
                            <div className="elt46">Add Details</div>
                            <div className="elt47"></div>
                        </div>
                        <div className="elt48">J’ai 2àans et je sélectionne les meilleurs prestataire pour que personne ne soit tromper.</div>
                    </div>
                </div>
            </div>

            <div className="main_third elt49">
                <div className="section_search">
                    <div className="title">Popular Dishes</div>
                    <div className="main_list">
                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande'/>
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella'/>
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>

                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande'/>
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella'/>
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>

                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande'/>
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella'/>
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>
                    </div>
                </div>

                <div className="section_search">
                    <div className="title">Recents Orders</div>
                    <div className="main_list">
                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande' />
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella' />
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>

                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande' />
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella' />
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>

                        <div className="elt_list elt50">
                            <img className="elt51" src={viande} alt='viande' />
                            <div className="elt52">This is one of our best dishes, you
                            can<br/> taste many delicacies.</div>
                            <div className="elt53">
                                <img className="elt54" src={hella} alt='hella' />
                                <div className="elt55">Prestataire</div>
                                <div className="elt56">Halim Maïmouna</div>
                            </div>
                            <div className="elt57">
                                <div className="elt58">Yaoundé</div>
                            </div>
                            <div className="plus">
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_button"></div>
                                <div className="plus_elt">
                                <div>Desactiver/Reactiver</div>
                                <div>Performances</div>
                                </div>
                            </div>
                            <div className="elt59">
                                <div className="elt60">
                                <div className="elt61">
                                    <div className="elt62"></div>
                                </div>
                                <div className="elt64">
                                    <div className="elt64"></div>
                                </div>
                                </div>
                                <div className="elt65">5.00</div>
                            </div>
                            <div className="elt66">Restaurant Akua</div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
        <img class="elt41" src={hello} alt='hello' />
    </div> 
  )
}

export default Dash2

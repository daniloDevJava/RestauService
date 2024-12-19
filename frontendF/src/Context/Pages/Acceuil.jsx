import React from 'react';
import Entete  from '../../components/Entete/Entete';
import Middle from '../../components/Middle/Middle';
import '../Pages/CSS/Acceuil.css';
import Footer from '../../components/Footer/Footer';

const Acceuil = () => {
    return (
        <div className='Acceuil'>
            <div className="ahead">
                <Entete/>
            </div>
            
            <div className="middle">
                 <Middle/>
            </div>

            <div className="Footer">
                <Footer/>
            </div>
            
           
        </div>
    );
};

export default Acceuil;
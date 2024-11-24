import React from "react";
import Accueil from '../components/Accueil/Accueil'
import PrestatairesList from "../components/Prestataire/PrestataireList";




const Home = () =>{
    return (
        <div>
            <Accueil/>
            <PrestatairesList/>
            
        </div>
    )
}

export default Home
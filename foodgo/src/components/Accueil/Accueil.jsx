import React,{useState} from "react";
import './Accueil.css';

const Accueil = () => {

    const [menu, setMenu] = useState("home");

    return (
        <section className="home">
            <div className="home-content">
                <h3>All over the world!</h3>
                <h1>Most Fasted Food <br/>Delivery Service</h1>
                <p>Get the best quality and most delicious food <br/>in the world
                you can have them all on our web <br/>application</p>

                <div className="btn-box">
                    <li onClick={()=>{setMenu("foods")}}>Order Food{menu==="foods"}</li>
                    <li onClick={()=>{setMenu("services")}}>View Menu{menu==="services"}</li>
                </div>
            </div>
            <span className="home-imgHover"></span>
        </section>
    )
}

export default Accueil
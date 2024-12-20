import { BrowserRouter,Routes,Route } from 'react-router-dom';
import Acceuil from './Context/Pages/Acceuil';
import Login from './Context/Pages/login';
import Sign from './Context/Pages/sign';
import Cart from './Context/Pages/cart';
import AcceuilClient from './Context/Pages/Acceuil_client';
import InfoPage from './Context/Pages/infoPage';
import './App.css';
import Dash2 from './components/Admin/Dash2';
import Dash3 from './components/Admin/Dash3';
import Dash4 from './components/Admin/Dash4';
import AddProduct from './components/PrestataireGestion/Add_product';


function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/' element={ <Acceuil/>} />
          <Route path='/Login' element={ <Login/>} />
          <Route path='/Sign' element={ <Sign/>} />
          <Route path='/Food' element = {<AcceuilClient/>}/>
          <Route path='/cart' element = {<Cart/>}/>
          <Route path='/Offers' element = {<AddProduct/>}/>
          <Route path='/information' element = {<InfoPage/>}/>
          <Route path='/Dashboard' element = {<Dash2/>}/>
          <Route path='/Dash3' element={ <Dash3/>} />
          <Route path='/Dash4' element={ <Dash4/>} />
        </Routes>
      </BrowserRouter>
     
    </div>
  );
}

export default App;

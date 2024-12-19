import { BrowserRouter,Routes,Route } from 'react-router-dom';
import Acceuil from './Context/Pages/Acceuil';
import Login from './Context/Pages/login';
import Sign from './Context/Pages/sign';
import Cart from './Context/Pages/cart';
import AcceuilClient from './Context/Pages/Acceuil_client';
import InfoPage from './Context/Pages/infoPage';
import './App.css';


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
          <Route path='/information' element = {<InfoPage/>}/>
        </Routes>
      </BrowserRouter>
     
    </div>
  );
}

export default App;

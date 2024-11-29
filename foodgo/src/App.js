
import './App.css';
import Navbar from './components/Navbar/Navbar';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Cart from './pages/Cart';
import LoginSignup from './pages/LoginSignup';
import Contact from './pages/Contact';
import Footer from './components/Footer/Footer';

function App() {
  return (
    <div className='main-content'>
      <BrowserRouter>
        <Navbar/>
        <Routes>
          <Route path='/' element={<Home/>} />

          <Route path='/contact' element={<Contact/>} />
          <Route path='/cart' element={<Cart/>}/>
          <Route path='/login' element={<LoginSignup/>}/>
        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;

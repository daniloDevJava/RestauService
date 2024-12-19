import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Navbar from '../../components/Navbar/Navbar';
import { loginUser } from '../../ajax/auth';
import '../Pages/CSS/login.css';

const Login = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [errors, setErrors] = useState({
    email: '',
    password: '',
  });

  const [globalError, setGlobalError] = useState(''); // Pour les erreurs générales
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false); // Etat pour gérer la visibilité du mot de passe

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    // Effacer les erreurs spécifiques si l'utilisateur modifie le champ
    setErrors((prev) => ({
      ...prev,
      [name]: '',
    }));
    setGlobalError('');
  };

  const validateForm = () => {
    let valid = true;
    const newErrors = { email: '', password: '' };

    if (!formData.email) {
      newErrors.email = "L'email est requis";
      valid = false;
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "L'email n'est pas valide";
      valid = false;
    }

    if (!formData.password) {
      newErrors.password = 'Le mot de passe est requis';
      valid = false;
    }

    setErrors(newErrors);
    return valid;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    setGlobalError(''); 

    try {
      
      await loginUser(formData.email, formData.password);
      navigate('/Food'); 
    } catch (error) {
      
      setGlobalError(error.message || 'Une erreur est survenue. Réessayez.');
    } finally {
      setLoading(false);
    }
  };

  
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="Log">
      <div className="Navbar">
        <Navbar />
      </div>
      <div className="conteneur">
        <h1>Se connecter</h1>
        <form onSubmit={handleSubmit}>
          <div className="groupe">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              name="email"
              id="email"
              placeholder="Entrez votre email"
              value={formData.email}
              onChange={handleChange}
              className={errors.email ? 'error' : ''}
            />
            {errors.email && <span className="error-message">{errors.email}</span>}
          </div>

          <div className="groupe">
            <label htmlFor="password">Mot de passe</label>
            <div className="password-container">
              <input
                type={showPassword ? 'text' : 'password'}
                name="password"
                id="password"
                placeholder="Entrez votre mot de passe"
                value={formData.password}
                onChange={handleChange}
                className={errors.password ? 'error' : ''}
              />
              <button
                type="button"
                className="password-toggle"
                onClick={togglePasswordVisibility}
                aria-label="Afficher/Cacher le mot de passe"
              >
                {showPassword ? '👁️' : '🙈'}
              </button>
            </div>
            {errors.password && <span className="error-message">{errors.password}</span>}
          </div>

          {/* Afficher l'erreur globale si nécessaire */}
          {globalError && <p className="error-message">{globalError}</p>}

          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Connexion...' : 'Se connecter'}
          </button>
        </form>

        <p className="signup-link">
          Vous n'avez pas de compte?{' '}
          <Link to="/sign" className="rouge">
            S'inscrire
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;

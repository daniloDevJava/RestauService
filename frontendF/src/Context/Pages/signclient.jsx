import React, { useState } from 'react';

const SignClient = ({ setRole }) => {
  const [formData, setFormData] = useState({
    prename: '',
    birthDate: '',
    cniNumber: '',
    address: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [errors, setErrors] = useState({}); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  const validateForm = () => {
    let newErrors = {};
    if (formData.password.length < 6) {
      newErrors.password = 'Le mot de passe doit comporter au moins 6 caractères.';
    }
  
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0; 
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      console.log('Données soumises par le client:', formData);
    }
  };

  return (
    <div className='conteneur'>
      <h1>S'inscrire en tant que Client</h1>
      <form onSubmit={handleSubmit}>
        <div className="groupe">
          <label htmlFor="prename">Prénom</label>
          <input 
            type="text"
            name="prename"
            id="prename"
            placeholder='Entrez votre prénom'
            value={formData.prename}
            onChange={handleChange}
            required
          />
        </div>
        <div className="groupe">
          <label htmlFor="birthDate">Date de naissance</label>
          <input 
            type="date"
            name="birthDate"
            id="birthDate"
            value={formData.birthDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="groupe">
          <label htmlFor="cniNumber">Numéro CNI</label>
          <input 
            type="text"
            name="cniNumber"
            id="cniNumber"
            placeholder='Entrez votre numéro de CNI'
            value={formData.cniNumber}
            onChange={handleChange}
            required
          />
        </div>
        <div className="groupe">
          <label htmlFor="address">Adresse</label>
          <input 
            type="text"
            name="address"
            id="address"
            placeholder='Entrez votre adresse'
            value={formData.address}
            onChange={handleChange}
            required
          />
        </div>
        <div className="groupe">
          <label htmlFor="email">Email</label>
          <input 
            type="email"
            name="email"
            id="email"
            placeholder='Entrez votre email'
            value={formData.email}
            onChange={handleChange}
            required
          />
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
              aria-label={showPassword ? "Cacher le mot de passe" : "Afficher le mot de passe"}
            >
              {showPassword ? '👁️' : '👁️‍🗨️'}
            </button>
          </div>
          {errors.password && <span className="error-message">{errors.password}</span>}
        </div>
        <div className="groupe">
          <label htmlFor="confirmPassword">Confirmer le mot de passe</label>
          <div className="password-container">
            <input
              type={showConfirmPassword ? 'text' : 'password'}
              name="confirmPassword"
              id="confirmPassword"
              placeholder='Confirmez votre mot de passe'
              value={formData.confirmPassword}
              onChange={handleChange}
            />
            <button
              type="button"
              className="password-toggle"
              onClick={toggleConfirmPasswordVisibility}
              aria-label={showConfirmPassword ? "Cacher le mot de passe" : "Afficher le mot de passe"}
            >
              {showConfirmPassword ? '👁️' : '👁️‍🗨️'}
            </button>
          </div>
        </div>
        <div className="groupe terms-group">
          <div className="terms-checkbox">
            <input
              type="checkbox"
              name="acceptTerms"
              id="acceptTerms"
              checked={formData.acceptTerms}
              onChange={handleChange}
              required
            />
            <label htmlFor="acceptTerms">
              En continuant, j'accepte les <a href="/terms" className="rouge">conditions d'utilisation</a>
            </label>
          </div>
        </div>

        <div className="button-group">
          <button type="button" onClick={() => setRole('')} className="back-button">
            Retour
          </button>
          <button type="submit" className="submit-button">
            S'inscrire
          </button>
        </div>
      </form>
    </div>
  );
};

export default SignClient;
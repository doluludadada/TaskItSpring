// src/components/LoginPage.js

import React, { useState } from 'react';
import './loginpage.css';
import axios from 'axios';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isSignIn, setIsSignIn] = useState(true);
  const [error, setError] = useState('');

  const handleToggle = () => {
    setIsSignIn(!isSignIn);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/auth/login', {
        username,
        password,
      });
      console.log('Login Successful', response.data);
      // Redirect user after successful login or display success message
    } catch (err) {
      setError('Invalid credentials');
    }
  };

  const handleSignUp = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/auth/signup', {
        username,
        password,
      });
      console.log('Sign Up Successful', response.data);
      setIsSignIn(true); // Switch to sign-in after successful sign-up
    } catch (err) {
      setError('Error creating account');
    }
  };

  return (
    <div className="login login-2 d-flex flex-column flex-root">
      <div className="login-aside d-flex flex-row-auto position-relative">
        {/* Background Illustration */}
      </div>
      <div className="content d-flex flex-column w-100">
        <div className="d-flex flex-column justify-content-center align-items-center text-center pt-5">
          <h3 className="display4 font-weight-bolder my-7 text-dark" style={{ color: '#986923' }}>
            TaskIT
          </h3>
        </div>
        <div className="d-flex flex-column-fluid flex-center mt-30 mt-lg-0">
          {isSignIn ? (
            <div className="login-form">
              <form onSubmit={handleLogin}>
                <div className="text-center pb-8">
                  <h2 className="font-weight-bolder text-dark font-size-h2 font-size-h1-lg">Welcome Back!</h2>
                </div>
                <div className="form-group">
                  <label className="font-size-h6 font-weight-bolder text-dark">Username</label>
                  <input
                    type="text"
                    className="form-control"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group position-relative">
                  <label className="font-size-h6 font-weight-bolder text-dark pt-5">Password</label>
                  <input
                    type="password"
                    className="form-control"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                {error && <div className="text-danger text-center pt-2">{error}</div>}
                <div className="text-center pt-2">
                  <button type="submit" className="btn btn-dark font-weight-bolder font-size-h6 px-8 py-4 my-3">
                    Login
                  </button>
                </div>
                <div className="text-center my-7">
                  <span className="separator-or">OR</span>
                </div>
                <div className="text-center">
                  <button
                    type="button"
                    className="btn btn-light-primary font-weight-bolder px-8 py-4 my-3"
                    onClick={handleToggle}
                  >
                    Create an Account
                  </button>
                </div>
              </form>
            </div>
          ) : (
            <div className="login-form">
              <form onSubmit={handleSignUp}>
                <div className="text-center pb-8">
                  <h2 className="font-weight-bolder text-dark font-size-h2 font-size-h1-lg">Sign Up</h2>
                  <p className="text-muted font-weight-bold font-size-h4">
                    Enter your details to create your account
                  </p>
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className="form-control"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                {error && <div className="text-danger text-center pt-2">{error}</div>}
                <div className="form-group d-flex flex-wrap flex-center pb-lg-0 pb-3">
                  <button
                    type="submit"
                    className="btn btn-primary font-weight-bolder font-size-h6 px-8 py-4 my-3 mx-4"
                  >
                    Submit
                  </button>
                  <button
                    type="button"
                    className="btn btn-light-primary font-weight-bolder font-size-h6 px-8 py-4 my-3 mx-4"
                    onClick={handleToggle}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default LoginPage;

// src/components/HomePage.js

import React from 'react';
import { Link } from 'react-router-dom';
import '../assets/styles/homepage.css'; // 匯入 CSS

function HomePage({ username, onLogout }) {
  return (
    <div className="home-container">
      <header>
        <h1>歡迎，{username}！</h1>
        <button onClick={onLogout} className="btn btn-secondary">登出</button>
      </header>
      <main>
        <div className="button-container">
          <Link to="/personaltask" className="btn btn-primary">個人任務管理</Link>
          <Link to="/projectmanagement" className="btn btn-primary">項目任務管理</Link>
        </div>
      </main>
    </div>
  );
}

export default HomePage;

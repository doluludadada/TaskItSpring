// src/App.js

import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import axios from 'axios';
import './App.css';

function App() {
  // 定義狀態變數，用於保存 JWT Token 和使用者資訊
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [username, setUsername] = useState('');

  useEffect(() => {
    if (token) {
      // 使用 Axios 呼叫後端 API 獲取當前使用者資訊
      axios.get('http://localhost:8964/api/user/me', {
        headers: {
          'Authorization': `Bearer ${token}`, // 在要求標頭中新增 JWT Token
        },
      })
      .then(response => {
        setUsername(response.data.username); // 設定使用者名稱
      })
      .catch(error => {
        console.error('獲取使用者資訊失敗:', error);
        // 如果獲取使用者資訊失敗，移除 Token 並重設狀態
        setToken('');
        localStorage.removeItem('token');
      });
    }
  }, [token]); // 當 Token 變化時重新獲取使用者資訊

  // 定義登錄函數，用於保存 Token
  const handleLogin = (newToken) => {
    setToken(newToken); // 更新狀態
    localStorage.setItem('token', newToken); // 保存 Token 到 localStorage
  };

  // 定義登出函數，用於移除 Token
  const handleLogout = () => {
    setToken(''); // 清空狀態
    localStorage.removeItem('token'); // 移除 localStorage 中的 Token
  };

  return (
    <Router>
      <Routes>
        {/* 根路徑：如果使用者已登錄，顯示 HomePage，否則重新導向到 LoginPage */}
        <Route path="/" element={token ? <HomePage username={username} onLogout={handleLogout} /> : <Navigate to="/login" />} />
        {/* 登錄頁面 */}
        <Route path="/login" element={<LoginPage onLogin={handleLogin} />} />
        {/* 其他受保護的路由，可以在此新增更多 Route */}
      </Routes>
    </Router>
  );
}

export default App;

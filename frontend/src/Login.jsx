import { useState } from 'react';
import axios from 'axios';
import React from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';


function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // 假设后端登录接口是 POST /api/auth/login
      const res = await axios.post('http://localhost:8080/api/auth/login', {
        username,
        password
      });
      if (res.data === 'Login success') {
        // 存储一个标记：表示已登录
        localStorage.setItem('isLoggedIn', 'true');
        setMsg('登录成功');
        // 跳转到聊天室列表页面
        navigate('/rooms');
      } else {
        setMsg('登录失败: ' + res.data);
      }
    } catch (err) {
      console.error(err);
      setMsg('登录失败: ' + err);
    }
  };

  return (
    <div className='container'>
      <h2>登录</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>用户名:</label>
          <input
            value={username}
            onChange={e=>setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>密码:</label>
          <input
            type="password"
            value={password}
            onChange={e=>setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">登录</button>
      </form>
      <p>{msg}</p>
    </div>
  );
}

export default Login;

import { useState } from 'react';
import axios from 'axios';
import React from 'react';
import './App.css';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      // 假设后端注册接口是 POST /api/auth/register
      const res = await axios.post('http://localhost:8080/api/auth/register', {
        username,
        password
      });
      setMsg(res.data); // e.g. "User registered successfully"
    } catch (err) {
      console.error(err);
      setMsg('注册失败: ' + err);
    }
  };

  return (
    <div className='container'>
      <h2>注册</h2>
      <form onSubmit={handleRegister}>
        <div>
          <label>用户名:</label>
          <input
            type="text"
            value={username}
            onChange={e=> setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>密码:</label>
          <input
            type="password"
            value={password}
            onChange={e=> setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">注册</button>
      </form>
      <p>{msg}</p>
    </div>
  );
}

export default Register;

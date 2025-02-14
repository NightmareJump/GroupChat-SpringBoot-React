

import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Register from './Register';
import React from 'react';
import Login from './Login';
import {ProtectedRoute} from './ProtectedRoute';
import RoomList from './RoomList';
import ChatRoom from './chatRoom';

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/register">注册</Link> | <Link to="/login">登录</Link>
      </nav>
      <Routes>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/rooms"
          element={
            <ProtectedRoute>
              <RoomList />
            </ProtectedRoute>
          }
        />
        <Route
          path="/chat/:roomId"
          element={
            <ProtectedRoute>
              <ChatRoom />
            </ProtectedRoute>
          }
        />

        {/* 其他路径默认跳转到 /login 或 /rooms */}
        <Route path="/" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

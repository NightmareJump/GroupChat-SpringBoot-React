import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function RoomList() {
  const [rooms, setRooms] = useState([]);
  const [newRoomName, setNewRoomName] = useState('');
  const [msg, setMsg] = useState('');

  // 第一次加载时，获取房间列表
  useEffect(() => {
    fetchRooms();
  }, []);

  const fetchRooms = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/rooms');
      setRooms(res.data); // e.g. [ {id:1, name:'技术讨论'}, ... ]
    } catch (err) {
      console.error(err);
      setMsg('获取房间失败:' + err);
    }
  };

  // 创建新房间
  const createRoom = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/api/rooms', {
        name: newRoomName
        // 视后端需求, 也可加 description
      });
      setMsg('房间创建成功: ' + JSON.stringify(res.data));
      setNewRoomName('');
      // 重新获取房间列表
      fetchRooms();
    } catch (err) {
      console.error(err);
      setMsg('创建房间失败: ' + err);
    }
  };

  return (
    <div style={{ margin: '20px' }}>
      <h2>聊天室列表</h2>
      {/* 添加聊天室表单 */}
      <form onSubmit={createRoom} style={{ marginBottom: '20px' }}>
        <label>新聊天室名称: </label>
        <input
          value={newRoomName}
          onChange={e=> setNewRoomName(e.target.value)}
          required
        />
        <button type="submit">添加聊天室</button>
      </form>

      <ul style={{ listStyleType: 'none', paddingLeft: 0 }}>
        {rooms.map(room => (
          <li key={room.id} style={{ margin: '8px 0' }}>
            {/* 点击后跳转到 /chat/:roomId */}
            <Link to={`/chat/${room.id}`} style={{ textDecoration: 'none' }}>
              <div
                style={{
                  border: '1px solid #ccc',
                  padding: '8px',
                  borderRadius: '4px',
                  backgroundColor: '#f9f9f9',
                  cursor: 'pointer'
                }}
              >
                {room.name}
              </div>
            </Link>
          </li>
        ))}
      </ul>
      <p>{msg}</p>
    </div>
  );
}

export default RoomList;

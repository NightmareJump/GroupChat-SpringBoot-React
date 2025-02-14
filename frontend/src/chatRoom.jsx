import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useParams } from 'react-router-dom';

function ChatRoom() {
  const { roomId } = useParams();
  const [messages, setMessages] = useState([]);
  const [roomName, setRoomName] = useState('');
  const [input, setInput] = useState('');
  const stompClientRef = useRef(null);

  useEffect(() => {
    // 1. 获取房间信息
    fetchRoomDetail(roomId);
    // 2. 获取历史消息
    fetchMessages(roomId);
    // 3. 建立 WebSocket(STOMP) 连接
    connectWebSocket(roomId);

    return () => {
      // 组件卸载时断开连接
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };
  }, [roomId]);

  // REST获取房间详情
  const fetchRoomDetail = async (id) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/rooms/${id}`);
      setRoomName(res.data.name);
    } catch (err) {
      console.error(err);
    }
  };

  // REST获取历史消息
  const fetchMessages = async (id) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/rooms/${id}/messages`);
      setMessages(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 建立STOMP连接
  const connectWebSocket = (id) => {
    const socket = new SockJS('http://localhost:8080/ws-chat');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('Connected to STOMP for room:', id);
        // 订阅此房间消息主题
        stompClient.subscribe(`/topic/messages/${id}`, (message) => {
          if (message.body) {
            const msg = JSON.parse(message.body);
            setMessages((prev) => [...prev, msg]);
          }
        });
      }
    });
    stompClient.activate();
    stompClientRef.current = stompClient;
  };

  // 发送消息
  const sendMessage = () => {
    if (!input.trim()) return;
    const msgObj = { 
      senderId: 1, // 示例
      content: input 
    };
    // 发到 /app/chat/{roomId}, 后端@MessageMapping("/chat/{roomId}")
    stompClientRef.current.publish({
      destination: `/app/chat/${roomId}`,
      body: JSON.stringify(msgObj)
    });
    setInput('');
  };

  return (
    <div style={{ margin: '20px' }}>
      <h2>房间 #{roomId} - {roomName}</h2>
      <div style={{
        border: '1px solid #ccc', 
        height: '300px', 
        overflowY: 'auto', 
        marginBottom: '10px',
        padding: '8px'
      }}>
        {messages.map((m, i) => (
          <div key={i}>
            <strong>{m.sender ? m.sender.username : m.senderId}:</strong> {m.content}
          </div>
        ))}
      </div>
      <input
        style={{ width:'220px' }}
        value={input}
        onChange={e => setInput(e.target.value)}
        placeholder="输入消息..."
      />
      <button onClick={sendMessage}>发送</button>
    </div>
  );
}

export default ChatRoom;

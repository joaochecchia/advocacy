package com.advocacychat.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setChatId(Long idUsuario, Long idChat){
        String key = "usuario:" + idUsuario + ":chat";
        redisTemplate.opsForValue().set(key, idChat);
    }

    public Long getChatId(Long idUsuario){
        String key = "usuario:" + idUsuario + ":chat";
        Object value = redisTemplate.opsForValue().get(key);

        return (value instanceof Long) ? (Long) value : null;
    }

    public void deletarChatId(Long idUsuario){
        String key = "usuario:" + idUsuario + ":chat";
        redisTemplate.delete(key);
    }
}

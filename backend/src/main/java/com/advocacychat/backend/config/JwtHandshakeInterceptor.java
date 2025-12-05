package com.advocacychat.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final TokenConfig tokenConfig;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }

        var servlet = servletRequest.getServletRequest();
        var token = servlet.getParameter("token");

        if (token == null || token.isBlank()) {
            System.out.println("Token não informado");
            return false;
        }

        var jwtData = tokenConfig.verifyToken(token);

        if (jwtData.isEmpty()) {
            System.out.println("Token inválido");
            return false;
        }

        attributes.put("user", jwtData.get());

        System.out.println("WebSocket autenticado: " + jwtData.get().email());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {}
}


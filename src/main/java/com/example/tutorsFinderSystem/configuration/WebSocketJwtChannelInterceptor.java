package com.example.tutorsFinderSystem.configuration;

import com.example.tutorsFinderSystem.exceptions.AppException;
import com.example.tutorsFinderSystem.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketJwtChannelInterceptor implements ChannelInterceptor {

    private final CustomJwtDecoder customJwtDecoder;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            // Lấy header từ STOMP CONNECT
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null) authHeader = accessor.getFirstNativeHeader("authorization");

            String token = extractBearerToken(authHeader);
            if (token == null) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            Jwt jwt;
            try {
                jwt = customJwtDecoder.decode(token);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            String email = extractEmail(jwt);
            if (email == null || email.isBlank()) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            Collection<SimpleGrantedAuthority> authorities = extractAuthorities(jwt);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            // QUAN TRỌNG: set Principal cho websocket session
            accessor.setUser(authentication);
        }

        return message;
    }

    private String extractBearerToken(String authHeader) {
        if (authHeader == null) return null;
        String h = authHeader.trim();
        if (h.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return h.substring(7).trim();
        }
        return null;
    }

    private String extractEmail(Jwt jwt) {
        Object email = jwt.getClaims().get("email");
        if (email instanceof String s && !s.isBlank()) return s;

        String sub = jwt.getSubject();
        if (sub != null && !sub.isBlank()) return sub;

        return null;
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        List<SimpleGrantedAuthority> out = new ArrayList<>();

        Object scope = jwt.getClaims().get("scope");
        if (scope instanceof String s && !s.isBlank()) {
            for (String part : s.split("\\s+")) {
                if (!part.isBlank()) out.add(new SimpleGrantedAuthority(part.trim()));
            }
        }

        Object roles = jwt.getClaims().get("roles");
        if (roles instanceof List<?> list) {
            for (Object r : list) {
                if (r instanceof String rs && !rs.isBlank()) {
                    out.add(new SimpleGrantedAuthority(rs.trim()));
                }
            }
        }
        return out;
    }
}

package com.advocacychat.backend.controlller;

import com.advocacychat.backend.service.ChatRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RedisController {

    private final ChatRedisService chatRedisService;

    @GetMapping("/redis/{id}/{id2}")
    public ResponseEntity<Long> testarRedis(@PathVariable Long id, @PathVariable Long id2){
        Long asda = chatRedisService.getChatId(id);

        return ResponseEntity.ok(asda);
    }
}

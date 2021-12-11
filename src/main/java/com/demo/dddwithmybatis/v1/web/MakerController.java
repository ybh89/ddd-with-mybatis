package com.demo.dddwithmybatis.v1.web;

import com.demo.dddwithmybatis.v1.application.MakerService;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/maker")
@RequiredArgsConstructor
@RestController
public class MakerController {
    private final MakerService makerService;

    @PostMapping
    public ResponseEntity<Long> create(MakerRequest makerRequest)
    {
        Long makerId = makerService.create(makerRequest);
        return ResponseEntity.ok(makerId);
    }
}

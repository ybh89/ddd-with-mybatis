package com.demo.dddwithmybatis.v1.web;

import com.demo.dddwithmybatis.v1.application.MakerService;
import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import com.demo.dddwithmybatis.v1.dto.MakerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<Long> update(MakerRequest makerRequest)
    {
        Long makerId = makerService.update(makerRequest);
        return ResponseEntity.ok(makerId);
    }

    @GetMapping("/{makerId}")
    public ResponseEntity<MakerResponse> retrieve(@PathVariable("makerId") Long makerId)
    {
        MakerResponse makerResponse = makerService.retrieve(makerId);
        return ResponseEntity.ok(makerResponse);
    }
}

package com.demo.dddwithmybatis.v1.web;

import com.demo.dddwithmybatis.v1.application.MakerService;
import com.demo.dddwithmybatis.v1.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.v1.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.v1.dto.maker.MakerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/maker")
@RequiredArgsConstructor
@RestController
public class MakerController {
    private final MakerService makerService;

    @PostMapping
    public ResponseEntity<MakerResponse> create(MakerSaveRequest makerSaveRequest)
    {
        MakerResponse makerResponse = makerService.create(makerSaveRequest);
        return ResponseEntity.ok(makerResponse);
    }

    @PutMapping
    public ResponseEntity<MakerResponse> update(MakerUpdateRequest makerUpdateRequest)
    {
        MakerResponse makerResponse = makerService.update(makerUpdateRequest);
        return ResponseEntity.ok(makerResponse);
    }

    @GetMapping("/{makerId}")
    public ResponseEntity<MakerResponse> retrieve(@PathVariable("makerId") Long makerId)
    {
        MakerResponse makerResponse = makerService.retrieve(makerId);
        return ResponseEntity.ok(makerResponse);
    }
}

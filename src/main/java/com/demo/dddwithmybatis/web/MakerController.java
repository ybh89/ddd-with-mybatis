package com.demo.dddwithmybatis.web;

import com.demo.dddwithmybatis.application.MakerService;
import com.demo.dddwithmybatis.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.dto.maker.MakerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/maker")
@RequiredArgsConstructor
@RestController
public class MakerController
{
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

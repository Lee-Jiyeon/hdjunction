package com.hdjunction.server.docapi.controller;

import com.hdjunction.server.docapi.dto.VisitDto;
import com.hdjunction.server.docapi.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/visit")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<VisitDto>> getAllVisits() {
        return ResponseEntity.ok(visitService.getVisitList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisit(id));
    }

    @PostMapping
    public ResponseEntity<Long> createVisit(@RequestBody VisitDto dto) {
        Long savedId = visitService.createVisit(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();
        return ResponseEntity.created(location).body(savedId);
    }

    @PatchMapping
    public ResponseEntity<Long> updateVisit(@RequestBody VisitDto dto) {
        // 변경 불가 항목 - patientId, hospitalId, visitId
        Long updatedId = visitService.updateVisit(dto);
        return ResponseEntity.ok(dto.getVisitId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.ok().build();
    }
}

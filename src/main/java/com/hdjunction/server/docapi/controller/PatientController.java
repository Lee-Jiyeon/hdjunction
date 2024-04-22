package com.hdjunction.server.docapi.controller;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getPatientList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPatient(@RequestBody PatientDto dto) {
        Long savedId = patientService.createPatient(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();
        return ResponseEntity.created(location).body(savedId);
    }

    @PatchMapping
    public ResponseEntity<Long> updatePatient(@RequestBody PatientDto dto) {
        // 변경 불가 항목 - patientId, hospitalId
        Long updatedId = patientService.updatePatient(dto);
        return ResponseEntity.ok(dto.getPatientId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}

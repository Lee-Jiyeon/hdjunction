package com.hdjunction.server.docapi.controller;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.dto.SearchDto;
import com.hdjunction.server.docapi.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDto.Info>> getAllPatients() {
        log.info("전체 환자 리스트를 조회합니다.");
        return ResponseEntity.ok(patientService.getPatientList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto.Info> getPatientById(@PathVariable Long id) {
        log.info("[patientId: {}] 환자 정보를 조회합니다.", id);
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @GetMapping("/lookup")
    public ResponseEntity<Page<PatientDto.LookupList>> getAllPatient(@RequestHeader Long hospitalId,
                                                                     @RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) String rgstNum,
                                                                     @RequestParam(required = false) String birthDate,
                                                                     @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("[hospitalId: {}] 병원의 모든 환자 정보를 조회합니다. (검색 조건 => 이름 [{}] 등록번호 [{}] 생년월일 [{}] 페이지 => {}/{})"
                , hospitalId, name, rgstNum, birthDate, pageNo, pageSize);
        SearchDto.Patient searchDto = SearchDto.Patient.builder()
                                                    .patientName(name)
                                                    .rgstNum(rgstNum)
                                                    .birthDate(birthDate)
                                                    .build();
        // pageNo은 1부터 시작, 0부터 인덱싱하기 위해 -1 해줌.
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        return ResponseEntity.ok(patientService.getPatientList(hospitalId, searchDto, pageRequest));
    }

    @GetMapping("/lookup/{id}")
    public ResponseEntity<PatientDto.Lookup> getPatientById(@RequestHeader Long hospitalId,
                                                            @PathVariable Long id) {
        log.info("[patientId: {} / hospitalId: {}] 내원한 환자 정보를 조회합니다.", id, hospitalId);
        return ResponseEntity.ok(patientService.getPatient(id, hospitalId));
    }

    @PostMapping
    public ResponseEntity<Long> createPatient(@RequestBody PatientDto.Create dto) {
        log.info("[hospitalId: {}] 신규 환자를 추가합니다.", dto.getHospitalId());
        Long savedId = patientService.createPatient(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedId)
                .toUri();
        return ResponseEntity.created(location).body(savedId);
    }

    @PatchMapping
    public ResponseEntity<Long> updatePatient(@RequestBody PatientDto.Update dto) {
        log.info("[patientId: {}] 환자 정보를 수정합니다.", dto.getPatientId());
        // 변경 불가 항목 - patientId, hospitalId
        Long updatedId = patientService.updatePatient(dto);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info("[patientId: {}] 환자 정보를 삭제합니다.", id);
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}

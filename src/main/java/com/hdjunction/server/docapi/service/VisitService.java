package com.hdjunction.server.docapi.service;

import com.hdjunction.server.docapi.dto.HospitalDto;
import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.dto.VisitDto;
import com.hdjunction.server.docapi.exception.CustomException;
import com.hdjunction.server.docapi.exception.ErrorCode;
import com.hdjunction.server.docapi.repository.HospitalRepository;
import com.hdjunction.server.docapi.repository.PatientRepository;
import com.hdjunction.server.docapi.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;

    public List<VisitDto> getVisitList() {
        return visitRepository.findAll().stream()
                .map(entity -> VisitDto.toDto(entity))
                .toList();
    }

    public VisitDto getVisit(Long id) {
        return visitRepository.findById(id)
                .map(entity -> VisitDto.toDto(entity))
                .orElseThrow(() -> new CustomException(ErrorCode.VISIT_NOT_FOUND));
    }

    public Long createVisit(VisitDto dto) {
        //기등록 방문 기록 update 방지
        if (visitRepository.existsById(dto.getVisitId()))
            throw new CustomException(ErrorCode.VISIT_ALREADY_EXIST);

        HospitalDto hospitalDto = hospitalRepository.findById(dto.getHospitalId())
                .map(entity -> HospitalDto.toDto(entity))
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));
        PatientDto patientDto = patientRepository.findById(dto.getPatientId())
                .map(entity -> PatientDto.toDto(entity))
                .orElseThrow(() -> new CustomException(ErrorCode.PATIENT_NOT_FOUND));

        return visitRepository.save(VisitDto.toEntity(dto, hospitalDto, patientDto)).getVisitId();
    }

    public Long updateVisit(VisitDto dto) {
        // 미등록 방문 기록 insert 방지
        if (!visitRepository.existsById(dto.getVisitId()))
            throw new CustomException(ErrorCode.VISIT_NOT_FOUND);

        return visitRepository.save(VisitDto.toEntity(dto)).getVisitId();
    }

    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id))
            throw new CustomException(ErrorCode.VISIT_NOT_FOUND);

        visitRepository.deleteById(id);
    }
}

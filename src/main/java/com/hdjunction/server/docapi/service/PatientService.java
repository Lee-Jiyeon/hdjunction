package com.hdjunction.server.docapi.service;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.exception.CustomException;
import com.hdjunction.server.docapi.exception.ErrorCode;
import com.hdjunction.server.docapi.repository.HospitalRepository;
import com.hdjunction.server.docapi.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;

    public List<PatientDto> getPatientList() {
        return patientRepository.findAll().stream()
                .map(entity -> PatientDto.toDto(entity))
                .toList();
    }

    public PatientDto getPatient(Long id) {
        return patientRepository.findById(id)
                .map(entity -> PatientDto.toDto(entity))
                .orElseThrow(() -> new CustomException(ErrorCode.PATIENT_NOT_FOUND));
    }

    public Long createPatient(PatientDto dto) {
        // 기등록 환자 update 방지
        if (patientRepository.existsById(dto.getPatientId()))
            throw new CustomException(ErrorCode.PATIENT_ALREADY_EXIST);

        Hospital hospitalEntity = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));
        return patientRepository.save(PatientDto.toEntity(dto, hospitalEntity)).getPatientId();
    }

    public Long updatePatient(PatientDto dto) {
        // 미등록 환자 insert 방지
        if (!patientRepository.existsById(dto.getPatientId()))
            throw new CustomException(ErrorCode.PATIENT_NOT_FOUND);

        return patientRepository.save(PatientDto.toEntity(dto, null)).getPatientId();
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id))
            throw new CustomException(ErrorCode.PATIENT_NOT_FOUND);

        patientRepository.deleteById(id);
    }
}

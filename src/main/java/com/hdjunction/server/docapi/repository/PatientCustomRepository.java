package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.dto.SearchDto;
import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientCustomRepository {
    // 병원별 최종 환자 등록 번호 조회
    String findPatientMaxRgstNumByHospital(Hospital hospital);

    // 병원별 환자 정보 조회
    Patient findPatientInfoByHospital(Long patientId, Long hospitalId);

    // 병원별 모든 환자 정보 조회
    Page<PatientDto.LookupList> findAllPatientInfoByHospital(Long hospitalId, SearchDto.Patient searchDto, Pageable pageable);
}

package com.hdjunction.server.docapi.service;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.dto.VisitDto;
import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import com.hdjunction.server.docapi.exception.CustomException;
import com.hdjunction.server.docapi.exception.ErrorCode;
import com.hdjunction.server.docapi.repository.HospitalRepository;
import com.hdjunction.server.docapi.repository.PatientCustomRepository;
import com.hdjunction.server.docapi.repository.PatientRepository;
import com.hdjunction.server.docapi.repository.VisitCustomRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientCustomRepository patientCustomRepository;
    private final VisitCustomRepositoryImpl visitCustomRepository;

    /**
     * 전체 환자 리스트를 조회한다.
     *
     * @return 데이터베이스에 저장된 전체 환자 리스트
     */
    public List<PatientDto.Info> getPatientList() {
        return patientRepository.findAll().stream()
                .map(entity -> PatientDto.Info.toDto(entity))
                .toList();
    }

    /**
     * patient id로 특정 환자의 정보를 조회한다.
     *
     * @param id 환자 고유의 patient id
     * @return 환자 정보 객체
     * @throws CustomException
     */
    public PatientDto.Info getPatient(Long id) {
        return patientRepository.findById(id)
                .map(entity -> PatientDto.Info.toDto(entity))
                .orElseThrow(() -> new CustomException(ErrorCode.PATIENT_NOT_FOUND));
    }

    /**
     * 병원의 전체 환자 리스트를 조회한다.
     *
     * @param hospitalId 병원 고유의 hospital id
     * @return 병원에 등록된 전체 환자 리스트
     */
    public List<PatientDto.LookupList> getPatientList(Long hospitalId) {
        return patientCustomRepository.findAllPatientInfoByHospital(hospitalId);
    }

    /**
     * patient id와 hospital id로 특정 환자의 정보 및 내원 기록을 조회한다.
     *
     * @param patientId 환자 고유의 patient id
     * @param hospitalId 내원한 병원의 hospital id
     * @return 환자 정보 및 내원 기록 조회 객체
     * @throws CustomException
     */
    public PatientDto.Lookup getPatient(Long patientId, Long hospitalId) {
        if (!hospitalRepository.existsById(hospitalId))
            throw new CustomException(ErrorCode.HOSPITAL_NOT_FOUND);

        Patient patient = patientCustomRepository.findPatientInfoByHospital(patientId, hospitalId);
        if (patient == null)
            throw new CustomException(ErrorCode.PATIENT_NOT_FOUND);

        PatientDto.Lookup patientDto = PatientDto.Lookup.toDto(patient);
        List<VisitDto> visitDto = VisitDto.toDtoList(visitCustomRepository.findAllVisitByPatient(patientId, hospitalId));
        patientDto.setVisitList(visitDto);

        return patientDto;
    }

    /**
     * 신규 환자 정보를 추가한다.
     * 필수 정보 : 병원 id, 환자 이름, 환자 성별
     *
     * @param dto 입력 받을 수 있는 생성용 환자 객체
     * @return 신규 생성된 환자의 patient id
     * @throws CustomException
     */
    @Transactional
    public Long createPatient(PatientDto.Create dto) {
        Hospital hospitalEntity = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));
        // 병원에 따라 마지막으로 등록된 등록 번호를 가져온다.
        String lastRgstNum = patientCustomRepository.findPatientMaxRgstNumByHospital(hospitalEntity);
        // 마지막 등록 번호에 1을 추가한 등록 번호 문자열(13자)을 생성한다.
        String newRgstNum = countRgstNum(lastRgstNum);

        return patientRepository.save(dto.toEntity(hospitalEntity, newRgstNum))
                .getPatientId();
    }

    /**
     * 신규 환자 정보를 추가한다.
     * 수정 가능한 정보 : 이름, 성별, 생일, 폰번호
     *
     * @param dto 수정 가능한 항목에 대한 수정용 환자 객체
     * @return 수정된 환자의 patient id
     * @throws CustomException
     */
    @Transactional
    public Long updatePatient(PatientDto.Update dto) {
        return patientRepository.findById(dto.getPatientId())
                .map(entity -> {
                    if(Strings.isNotEmpty(dto.getPatientName()))
                        entity.setPatientName(dto.getPatientName());
                    if (Strings.isNotEmpty(dto.getSexCode()))
                        entity.setSexCode(dto.getSexCode());
                    if(Strings.isNotEmpty(dto.getBirthDate()))
                        entity.setBirthDate(dto.getBirthDate());
                    if(Strings.isNotEmpty(dto.getPhoneNum()))
                        entity.setPhoneNum(dto.getPhoneNum());
                    return entity;
                })
                .orElseThrow(() -> new CustomException(ErrorCode.PATIENT_NOT_FOUND))
                .getPatientId();
    }

    /**
     * 환자 정보를 삭제한다.
     *
     * @param id 삭제할 환자의 patient id
     * @throws CustomException
     */
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id))
            throw new CustomException(ErrorCode.PATIENT_NOT_FOUND);

        patientRepository.deleteById(id);
    }

    /**
     * 환자 등록 번호 포맷에 맞춰 1 증가한 번호를 생성한다.
     *
     * @param currentNum 최종 환자 등록 번호
     * @return 최종 환자 등록 번호에서 1 더해진 신규 등록 번호
     * @throws CustomException
     */
    private static String countRgstNum(String currentNum) {
        long count = 0;
        if (currentNum != null)
            count = Long.parseLong(currentNum);
        return String.format("%013d", count + 1);
    }
}

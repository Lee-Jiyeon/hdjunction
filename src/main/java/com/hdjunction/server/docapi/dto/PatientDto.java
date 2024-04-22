package com.hdjunction.server.docapi.dto;

import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDto {
    private Long patientId;
    private Long hospitalId;
    private String patientName;
    private String rgstNum;
    private String sexCode;
    private String birthDate;
    private String phoneNum;

    @Builder
    public PatientDto(Long patientId, Long hospitalId, String patientName, String rgstNum, String sexCode, String birthDate, String phoneNum) {
        this.patientId = patientId;
        this.hospitalId = hospitalId;
        this.patientName = patientName;
        this.rgstNum = rgstNum;
        this.sexCode = sexCode;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }

    public static PatientDto toDto(Patient entity) {
        return PatientDto.builder()
                .patientId(entity.getPatientId())
                .hospitalId(entity.getHospital().getHospId())
                .patientName(entity.getPatientName())
                .rgstNum(entity.getRgstNum())
                .sexCode(entity.getSexCode())
                .birthDate(entity.getBirthDate())
                .phoneNum(entity.getPhoneNum())
                .build();
    }

    public static List<PatientDto> toDtoList(List<Patient> list) {
        return list.stream()
                .map(entity -> PatientDto.toDto(entity))
                .toList();
    }

    public static Patient toEntity(PatientDto dto, Hospital hospital) {
        return Patient.builder()
                .patientId(dto.patientId)
                .hospital(hospital)
                .patientName(dto.patientName)
                .rgstNum(dto.rgstNum)
                .sexCode(dto.sexCode)
                .birthDate(dto.birthDate)
                .phoneNum(dto.phoneNum)
                .build();
    }
}

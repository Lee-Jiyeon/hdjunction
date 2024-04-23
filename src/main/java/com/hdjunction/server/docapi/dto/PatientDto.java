package com.hdjunction.server.docapi.dto;

import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private Long patientId;
        private Long hospitalId;
        private String patientName;
        private String rgstNum;
        private String sexCode;
        private String birthDate;
        private String phoneNum;

        public static PatientDto.Info toDto(Patient entity) {
            return PatientDto.Info.builder()
                    .patientId(entity.getPatientId())
                    .hospitalId(entity.getHospital().getHospId())
                    .patientName(entity.getPatientName())
                    .rgstNum(entity.getRgstNum())
                    .sexCode(entity.getSexCode())
                    .birthDate(entity.getBirthDate())
                    .phoneNum(entity.getPhoneNum())
                    .build();
        }

        public Patient toEntity(Hospital hospital) {
            return com.hdjunction.server.docapi.entity.Patient.builder()
                    .patientId(this.patientId)
                    .hospital(hospital)
                    .patientName(this.patientName)
                    .rgstNum(this.rgstNum)
                    .sexCode(this.sexCode)
                    .birthDate(this.birthDate)
                    .phoneNum(this.phoneNum)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Create {
        @NotBlank
        private Long hospitalId;
        @NotBlank
        private String patientName;
        @NotBlank
        private String sexCode;
        private String birthDate;
        private String phoneNum;

        public Patient toEntity(Hospital hospital, String rgstNum) {
            return Patient.builder()
                    .hospital(hospital)
                    .patientName(patientName)
                    .rgstNum(rgstNum)
                    .sexCode(sexCode)
                    .birthDate(birthDate)
                    .phoneNum(phoneNum)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Update {
        @NotBlank
        private Long patientId;
        private String patientName;
        private String sexCode;
        private String birthDate;
        private String phoneNum;

        public Patient toEntity() {
            return Patient.builder()
                    .patientId(this.patientId)
                    .patientName(this.patientName)
                    .sexCode(this.sexCode)
                    .birthDate(this.birthDate)
                    .phoneNum(this.phoneNum)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Lookup {
        private Long patientId;
        private Long hospitalId;
        private String patientName;
        private String rgstNum;
        private String sexCode;
        private String birthDate;
        private String phoneNum;
        @Builder.Default
        private List<VisitDto> visitList = new ArrayList<>();

        public static PatientDto.Lookup toDto(Patient entity) {
            return PatientDto.Lookup.builder()
                    .patientId(entity.getPatientId())
                    .hospitalId(entity.getHospital().getHospId())
                    .patientName(entity.getPatientName())
                    .rgstNum(entity.getRgstNum())
                    .sexCode(entity.getSexCode())
                    .birthDate(entity.getBirthDate())
                    .phoneNum(entity.getPhoneNum())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LookupList {
        private Long patientId;
        private Long hospitalId;
        private String patientName;
        private String rgstNum;
        private String sexCode;
        private String birthDate;
        private String phoneNum;
        private String recentVisitDate; // depth 추가될 경우 VisitDto로 변경
    }

}

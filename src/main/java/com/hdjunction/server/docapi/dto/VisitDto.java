package com.hdjunction.server.docapi.dto;

import com.hdjunction.server.docapi.entity.Visit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitDto {
    private Long visitId;
    private Long hospitalId;
    private Long patientId;
    private Timestamp receiptDate;
    private String visitStat;

    @Builder
    public VisitDto(Long visitId, Long hospitalId, Long patientId, Timestamp receiptDate, String visitStat) {
        this.visitId = visitId;
        this.hospitalId = hospitalId;
        this.patientId = patientId;
        this.receiptDate = receiptDate;
        this.visitStat = visitStat;
    }

    public static VisitDto toDto(Visit entity) {
        return VisitDto.builder()
                .visitId(entity.getVisitId())
                .hospitalId(entity.getHospital().getHospId())
                .patientId(entity.getPatient().getPatientId())
                .receiptDate(entity.getReceiptDate())
                .visitStat(entity.getVisitStat())
                .build();
    }

    public static List<VisitDto> toDtoList(List<Visit> list) {
        return list.stream()
                .map(entity -> VisitDto.toDto(entity))
                .toList();
    }

    public static Visit toEntity(VisitDto vDto, HospitalDto hDto, PatientDto.Info pDto) {
        return Visit.builder()
                .visitId(vDto.getVisitId())
                .hospital(hDto.toEntity())
                .patient(pDto.toEntity(hDto.toEntity()))
                .receiptDate(vDto.getReceiptDate())
                .visitStat(vDto.getVisitStat())
                .build();
    }

    public Visit toEntity() {
        return Visit.builder()
                .visitId(this.getVisitId())
                .receiptDate(this.getReceiptDate())
                .visitStat(this.getVisitStat())
                .build();
    }
}

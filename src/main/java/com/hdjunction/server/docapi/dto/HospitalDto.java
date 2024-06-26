package com.hdjunction.server.docapi.dto;

import com.hdjunction.server.docapi.entity.Hospital;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalDto {
    private Long hospId;
    private String hospName;
    private String healthInstNum;
    private String directorName;

    @Builder
    public HospitalDto(Long hospId, String hospName, String healthInstNum, String directorName) {
        this.hospId = hospId;
        this.hospName = hospName;
        this.healthInstNum = healthInstNum;
        this.directorName = directorName;
    }

    public static HospitalDto toDto(Hospital entity) {
        return HospitalDto.builder()
                .hospId(entity.getHospId())
                .hospName(entity.getHospName())
                .healthInstNum(entity.getHealthInstNum())
                .directorName(entity.getDirectorName())
                .build();
    }

    public Hospital toEntity() {
        return Hospital.builder()
                .hospId(this.getHospId())
                .hospName(this.getHospName())
                .healthInstNum(this.getHealthInstNum())
                .directorName(this.getDirectorName())
                .build();
    }
}

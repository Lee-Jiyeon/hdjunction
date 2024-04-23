package com.hdjunction.server.docapi.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Patient {
        private String patientName;
        private String rgstNum;
        private String birthDate;
    }
}
package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TI_PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PATIENT_ID", updatable=false)
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "HOSP_ID", updatable=false)
    private Hospital hospital;

    @Column(name="PATIENT_NAME")
    private String patientName;

    @Column(name="RGST_NUM")
    private String rgstNum;

    @Column(name="SEX_CODE")
    private String sexCode;

    @Column(name="BIRTH_DATE")
    private String birthDate;

    @Column(name="PHONE_NUM")
    private String phoneNum;

    @Column(name="CREATE_TIME", insertable=false, updatable=false)
    private Timestamp createTime;

    @Column(name="CREATE_ID", insertable=false, updatable=false)
    private String createId;

    @Column(name="UPDATE_TIME", insertable=false, updatable=false)
    private Timestamp updateTime;

    @Column(name="UPDATE_ID", insertable=false, updatable=false)
    private String updateId;

    @Builder
    public Patient(Long patientId, Hospital hospital, String patientName, String rgstNum, String sexCode, String birthDate, String phoneNum) {
        this.patientId = patientId;
        this.hospital = hospital;
        this.patientName = patientName;
        this.rgstNum = rgstNum;
        this.sexCode = sexCode;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }
}

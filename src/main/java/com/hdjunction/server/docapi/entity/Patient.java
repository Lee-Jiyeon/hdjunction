package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "TI_PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PATIENT_ID")
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "HOSP_ID")
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

    @Column(name="CREATE_TIME")
    private Timestamp createTime;

    @Column(name="CREATE_ID")
    private String createId;

    @Column(name="UPDATE_TIME")
    private Timestamp updateTime;

    @Column(name="UPDATE_ID")
    private String updateId;
}

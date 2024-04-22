package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TI_VISIT")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VISIT_ID", updatable=false)
    private Long visitId;

    @ManyToOne(targetEntity = Hospital.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSP_ID", updatable=false)
    private Hospital hospital;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", updatable=false)
    private Patient patient;

    @Column(name="RECEIPT_DATE")
    private Timestamp receiptDate;

    @Column(name="VISIT_STAT")
    private String visitStat;

    @Column(name="CREATE_TIME", insertable=false, updatable=false)
    private Timestamp createTime;

    @Column(name="CREATE_ID", insertable=false, updatable=false)
    private String createId;

    @Column(name="UPDATE_TIME", insertable=false, updatable=false)
    private Timestamp updateTime;

    @Column(name="UPDATE_ID", insertable=false, updatable=false)
    private String updateId;

    @Builder
    public Visit(Long visitId, Hospital hospital, Patient patient, Timestamp receiptDate, String visitStat) {
        this.visitId = visitId;
        this.hospital = hospital;
        this.patient = patient;
        this.receiptDate = receiptDate;
        this.visitStat = visitStat;
    }
}

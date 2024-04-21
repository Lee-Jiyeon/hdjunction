package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "TI_VISIT")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VISIT_ID")
    private Long visitId;

    @ManyToOne
    @JoinColumn(name = "HOSP_ID")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @Column(name="RECEIPT_DATE")
    private Timestamp receiptDate;

    @Column(name="VISIT_STAT")
    private String visitStat;

    @Column(name="CREATE_TIME")
    private Timestamp createTime;

    @Column(name="CREATE_ID")
    private String createId;

    @Column(name="UPDATE_TIME")
    private Timestamp updateTime;

    @Column(name="UPDATE_ID")
    private String updateId;
}

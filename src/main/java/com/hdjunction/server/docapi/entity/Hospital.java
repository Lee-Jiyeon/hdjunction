package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TI_HOSPITAL")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HOSP_ID")
    private Long hospId;

    @Column(name="HOSP_NAME")
    private String hospName;

    @Column(name="HEALTH_INST_NUM")
    private String healthInstNum;

    @Column(name="DIRECTOR_NAME")
    private String directorName;

    @Column(name="CREATE_TIME")
    private Timestamp createTime;

    @Column(name="CREATE_ID")
    private String createId;

    @Column(name="UPDATE_TIME")
    private Timestamp updateTime;

    @Column(name="UPDATE_ID")
    private String updateId;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients = new ArrayList<>();
}

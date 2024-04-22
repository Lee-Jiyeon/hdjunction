package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name="CREATE_TIME", insertable=false, updatable=false)
    private Timestamp createTime;

    @Column(name="CREATE_ID", insertable=false, updatable=false)
    private String createId;

    @Column(name="UPDATE_TIME", insertable=false, updatable=false)
    private Timestamp updateTime;

    @Column(name="UPDATE_ID", insertable=false, updatable=false)
    private String updateId;

    @Builder
    public Hospital(Long hospId, String hospName, String healthInstNum, String directorName) {
        this.hospId = hospId;
        this.hospName = hospName;
        this.healthInstNum = healthInstNum;
        this.directorName = directorName;
    }
}

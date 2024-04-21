package com.hdjunction.server.docapi.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="TI_CODE")
public class Code {
    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Codegroup codegroup;

    @Id
    @Column(name="CODE_ID")
    private String codeId;

    @Column(name="CODE_NAME")
    private String codeName;

    @Column(name="CREATE_TIME")
    private Timestamp createTime;

    @Column(name="CREATE_ID")
    private String createId;

    @Column(name="UPDATE_TIME")
    private Timestamp updateTime;

    @Column(name="UPDATE_ID")
    private String updateId;


}

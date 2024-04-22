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

    @Column(name="CREATE_TIME", insertable=false, updatable=false)
    private Timestamp createTime;

    @Column(name="CREATE_ID", insertable=false, updatable=false)
    private String createId;

    @Column(name="UPDATE_TIME", insertable=false, updatable=false)
    private Timestamp updateTime;

    @Column(name="UPDATE_ID", insertable=false, updatable=false)
    private String updateId;


}

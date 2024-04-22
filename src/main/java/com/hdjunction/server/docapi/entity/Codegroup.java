package com.hdjunction.server.docapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name="TI_CODE_GROUP")
public class Codegroup {
    @Id
    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="GROUP_NAME")
    private String groupName;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="CREATE_TIME", insertable=false, updatable=false)
    private Timestamp createTime;

    @Column(name="CREATE_ID", insertable=false, updatable=false)
    private String createId;

    @Column(name="UPDATE_TIME", insertable=false, updatable=false)
    private Timestamp updateTime;

    @Column(name="UPDATE_ID", insertable=false, updatable=false)
    private String updateId;
}

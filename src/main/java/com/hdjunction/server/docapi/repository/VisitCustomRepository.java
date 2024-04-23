package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.entity.Visit;

import java.util.List;

public interface VisitCustomRepository {
    List<Visit> findAllVisitByPatient(Long patientId, Long hospitalId);
}

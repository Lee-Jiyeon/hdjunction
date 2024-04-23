package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.entity.Visit;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.hdjunction.server.docapi.entity.QVisit.visit;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VisitCustomRepositoryImpl implements VisitCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Visit> findAllVisitByPatient(Long patientId, Long hospitalId) {
        return jpaQueryFactory
                .selectFrom(visit)
                .where(visit.patient.patientId.eq(patientId)
                        , visit.hospital.hospId.eq(hospitalId))
                .fetch();
    }
}

package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hdjunction.server.docapi.entity.QPatient.patient;
import static com.hdjunction.server.docapi.entity.QVisit.visit;

@Repository
@RequiredArgsConstructor
public class PatientCustomRepositoryImpl implements PatientCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public String findPatientMaxRgstNumByHospital(Hospital hospital) {
        return jpaQueryFactory
                .select(patient.rgstNum.max())
                .from(patient)
                .where(patient.hospital.eq(hospital))
                .fetchOne();
    }

    @Override
    public Patient findPatientInfoByHospital(Long patientId, Long hospitalId) {
        return jpaQueryFactory
                .selectFrom(patient)
                .where(patient.patientId.eq(patientId)
                        , patient.hospital.hospId.eq(hospitalId))
                .fetchOne();
    }

    @Override
    public List<PatientDto.LookupList> findAllPatientInfoByHospital(Long hospitalId) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "FORMATDATETIME({0}, {1})"
                , visit.receiptDate.max()
                , "Y-MM-dd");

        return jpaQueryFactory
                .select(Projections.fields(PatientDto.LookupList.class
                        , patient.patientId
                        , patient.hospital.hospId.as("hospitalId")
                        , patient.patientName
                        , patient.rgstNum
                        , patient.sexCode
                        , patient.birthDate
                        , patient.phoneNum
                        , formattedDate.as("recentVisitDate")
                ))
                .from(patient)
                .leftJoin(visit)
                .on(patient.eq(visit.patient))
                .where(patient.hospital.hospId.eq(hospitalId))
                .groupBy(patient.patientId)
                .fetch();
    }
}

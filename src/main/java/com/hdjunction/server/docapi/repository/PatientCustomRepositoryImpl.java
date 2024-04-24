package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.dto.SearchDto;
import com.hdjunction.server.docapi.entity.Hospital;
import com.hdjunction.server.docapi.entity.Patient;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hdjunction.server.docapi.entity.QPatient.patient;
import static com.hdjunction.server.docapi.entity.QVisit.visit;
import static org.springframework.util.StringUtils.hasText;

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
    public Page<PatientDto.LookupList> findAllPatientInfoByHospital(Long hospitalId,
                                                                    SearchDto.Patient searchDto,
                                                                    Pageable pageable) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "FORMATDATETIME({0}, {1})"
                , visit.receiptDate.max()
                , "Y-MM-dd");

        List<PatientDto.LookupList> patientList = jpaQueryFactory
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
                .where(patient.hospital.hospId.eq(hospitalId),
                        patientNameContains(searchDto.getPatientName()),
                        rgstNumStartWith(searchDto.getRgstNum()),
                        birthDateContains(searchDto.getBirthDate()))
                .groupBy(patient.patientId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = jpaQueryFactory
                .select(patient.count())
                .from(patient)
                .where(patient.hospital.hospId.eq(hospitalId),
                        patientNameContains(searchDto.getPatientName()),
                        rgstNumStartWith(searchDto.getRgstNum()),
                        birthDateContains(searchDto.getBirthDate()));

        return PageableExecutionUtils.getPage(patientList, pageable, totalCount::fetchOne);
    }

    private BooleanExpression patientNameContains(String patientName) {
        return hasText(patientName) ? patient.patientName.contains(patientName) : null;
    }

    private BooleanExpression rgstNumStartWith(String rgstNum) {
        return hasText(rgstNum) ? patient.rgstNum.startsWith(rgstNum) : null;
    }

    private BooleanExpression birthDateContains(String birthDate) {
        return hasText(birthDate) ? patient.birthDate.contains(birthDate) : null;
    }
}

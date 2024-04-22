package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}

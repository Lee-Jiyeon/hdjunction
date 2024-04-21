package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}

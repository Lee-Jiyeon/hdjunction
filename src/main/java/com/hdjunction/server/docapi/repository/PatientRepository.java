package com.hdjunction.server.docapi.repository;

import com.hdjunction.server.docapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository  extends JpaRepository<Patient, Long> {
}

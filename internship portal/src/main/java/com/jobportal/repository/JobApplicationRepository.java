package com.jobportal.repository;

import com.jobportal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
    List<JobApplication> findByApplicantEmail(String email);
}
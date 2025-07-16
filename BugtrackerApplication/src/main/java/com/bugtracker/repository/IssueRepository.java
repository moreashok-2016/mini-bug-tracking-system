package com.bugtracker.repository;

import com.bugtracker.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>,JpaSpecificationExecutor<Issue> {
    List<Issue> findByAssigneeId(Long id);
}
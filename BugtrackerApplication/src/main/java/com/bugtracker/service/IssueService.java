package com.bugtracker.service;

import com.bugtracker.dto.IssueDto;
import com.bugtracker.dto.IssueFilterRequest;

import java.util.List;

import org.springframework.data.domain.Page; // ✅ CORRECT

public interface IssueService {
	 IssueDto createIssue(IssueDto dto);
	    List<IssueDto> getAllIssues();
	    IssueDto getIssueById(Long id);
	    IssueDto updateIssue(Long id, IssueDto dto);
	    void deleteIssue(Long id);
	    Page<IssueDto> searchIssues(IssueFilterRequest filterRequest);
}
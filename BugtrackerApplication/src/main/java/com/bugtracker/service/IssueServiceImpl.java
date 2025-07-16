package com.bugtracker.service;

import com.bugtracker.dto.IssueDto;
import com.bugtracker.dto.IssueFilterRequest;
import com.bugtracker.exception.ResourceNotFoundException;
import com.bugtracker.model.Issue;
import com.bugtracker.model.User;
import com.bugtracker.repository.IssueRepository;
import com.bugtracker.repository.UserRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	 private final IssueRepository issueRepository;
	    private final UserRepository userRepository;
	    private final ModelMapper modelMapper;

    @Override
    public IssueDto createIssue(IssueDto dto) {
        User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee not found with id: " + dto.getAssigneeId()));
        Issue issue = mapToEntity(dto, assignee);
        return mapToDto(issueRepository.save(issue));
    }

    @Override
    public List<IssueDto> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public IssueDto getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        return mapToDto(issue);
    }

    @Override
    @Transactional
    public IssueDto updateIssue(Long id, IssueDto dto) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));

        User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee not found with id: " + dto.getAssigneeId()));

        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setPriority(dto.getPriority());
        issue.setStatus(dto.getStatus());
        issue.setAssignee(assignee);

        return mapToDto(issueRepository.save(issue));
    }

    @Override
    public void deleteIssue(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        issueRepository.delete(issue);
    }

    private IssueDto mapToDto(Issue issue) {
        return IssueDto.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .createdDate(issue.getCreatedDate())
                .assigneeId(issue.getAssignee() != null ? issue.getAssignee().getId() : null)
                .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getName() : null)
                .build();
    }

    private Issue mapToEntity(IssueDto dto, User assignee) {
        return Issue.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .createdDate(LocalDateTime.now())
                .assignee(assignee)
                .build();
    }
    
    @Override
    public Page<IssueDto> searchIssues(IssueFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());

        Page<Issue> resultPage = issueRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterRequest.getStatus()));
            }
            if (filterRequest.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), filterRequest.getPriority()));
            }
            if (filterRequest.getAssigneeId() != null) {
                predicates.add(cb.equal(root.get("assignee").get("id"), filterRequest.getAssigneeId()));
            }
            if (filterRequest.getTitle() != null && !filterRequest.getTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filterRequest.getTitle().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return resultPage.map(issue -> modelMapper.map(issue, IssueDto.class));
    }

}

package com.bugtracker.controller;

import com.bugtracker.dto.IssueDto;
import com.bugtracker.dto.IssueFilterRequest;
import com.bugtracker.service.IssueService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page; // ✅ CORRECT
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bugtracker.model.Status;
import com.bugtracker.model.Priority;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueDto dto) {
        return ResponseEntity.ok(issueService.createIssue(dto));
    }

    @GetMapping
    public ResponseEntity<List<IssueDto>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDto> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssueById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDto> updateIssue(@PathVariable Long id, @RequestBody IssueDto dto) {
        return ResponseEntity.ok(issueService.updateIssue(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public Page<IssueDto> searchIssues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) String title
    ) {
        IssueFilterRequest filterRequest = IssueFilterRequest.builder()
                .page(page)
                .size(size)
                .status(status != null ? Status.valueOf(status.toUpperCase()) : null)
                .priority(priority != null ? Priority.valueOf(priority.toUpperCase()) : null)
                .assigneeId(assigneeId)
                .title(title)
                .build();

        return issueService.searchIssues(filterRequest);
    }



}

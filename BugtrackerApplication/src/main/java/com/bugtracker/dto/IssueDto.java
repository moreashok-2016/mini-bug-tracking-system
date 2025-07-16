package com.bugtracker.dto;

import java.time.LocalDateTime;

import com.bugtracker.model.Priority;
import com.bugtracker.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime createdDate;
    private Long assigneeId;
    private String assigneeName;
}

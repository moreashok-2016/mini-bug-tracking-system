package com.bugtracker.dto;

import com.bugtracker.model.Priority;

import com.bugtracker.model.Status; // ✅ CORRECT
import lombok.Builder;
import lombok.Data;


@Data
//@Setter
//@Getter
@Builder
//@RequiredArgsConstructor
public class IssueFilterRequest 
{
	private Status status;
    private Priority priority;
    private Long assigneeId;
    private String title;
    private int page = 0;
    private int size = 10;
}

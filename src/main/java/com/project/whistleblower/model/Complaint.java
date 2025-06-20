package com.project.whistleblower.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Complaint {
    private String description;
    private List<String> evidenceUrl;
    private LocalDateTime createdAt =  LocalDateTime.now();
}

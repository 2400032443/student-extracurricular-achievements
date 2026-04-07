package com.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime achievedDate;
    private String certificate;
    private String studentName;
    private LocalDateTime createdAt;
}
package com.student.dto;

import com.student.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private String title;
    private String content;
    private Report.ReportType type;
    private LocalDateTime generatedAt;
    private String generatedByName;
}
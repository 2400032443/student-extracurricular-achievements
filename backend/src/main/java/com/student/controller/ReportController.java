package com.student.controller;

import com.student.dto.ReportDTO;
import com.student.entity.Report;
import com.student.entity.User;
import com.student.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<?> generateReport(@RequestBody Map<String, Object> request) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Report report = reportService.generateReport(
                    (String) request.get("title"),
                    (String) request.get("content"),
                    Report.ReportType.valueOf((String) request.get("type")),
                    currentUser
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Report generated successfully");
            response.put("reportId", report.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReports() {
        try {
            List<Report> reports = reportService.getAllReports();

            List<ReportDTO> reportDTOs = reports.stream()
                    .map(r -> new ReportDTO(
                            r.getId(),
                            r.getTitle(),
                            r.getContent(),
                            r.getType(),
                            r.getGeneratedAt(),
                            r.getGeneratedBy().getName()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(reportDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
package com.student.service;

import com.student.entity.Report;
import com.student.entity.User;
import com.student.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report generateReport(String title, String content, Report.ReportType type, User generatedBy) {
        Report report = new Report();
        report.setTitle(title);
        report.setContent(content);
        report.setType(type);
        report.setGeneratedBy(generatedBy);
        report.setActive(true);

        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAllByActiveTrue();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setActive(false);
        reportRepository.save(report);
    }
}
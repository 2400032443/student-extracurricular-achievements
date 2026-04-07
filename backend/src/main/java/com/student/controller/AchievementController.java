package com.student.controller;

import com.student.dto.AchievementDTO;
import com.student.entity.Achievement;
import com.student.entity.User;
import com.student.service.AchievementService;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addAchievement(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = ((Number) request.get("studentId")).longValue();
            User student = userService.getUserById(studentId);

            Achievement achievement = achievementService.addAchievement(
                    student,
                    (String) request.get("title"),
                    (String) request.get("description"),
                    (String) request.get("category"),
                    LocalDateTime.parse((String) request.get("achievedDate")),
                    (String) request.get("certificate")
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Achievement added successfully");
            response.put("achievementId", achievement.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAchievement(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Achievement achievement = achievementService.updateAchievement(
                    id,
                    (String) request.get("title"),
                    (String) request.get("description"),
                    (String) request.get("category"),
                    LocalDateTime.parse((String) request.get("achievedDate")),
                    (String) request.get("certificate")
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Achievement updated successfully");
            response.put("achievementId", achievement.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAchievements() {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Achievement> achievements;
            if (currentUser.getRole() == User.Role.ADMIN) {
                achievements = achievementService.getAchievementsByStudent(currentUser);
            } else {
                achievements = achievementService.getAchievementsByStudent(currentUser);
            }

            List<AchievementDTO> achievementDTOs = achievements.stream()
                    .map(a -> new AchievementDTO(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getCategory(),
                            a.getAchievedDate(),
                            a.getCertificate(),
                            a.getStudent().getName(),
                            a.getCreatedAt()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(achievementDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
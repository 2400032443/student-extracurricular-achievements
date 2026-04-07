package com.student.service;

import com.student.entity.Achievement;
import com.student.entity.User;
import com.student.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public Achievement addAchievement(User student, String title, String description, String category, LocalDateTime achievedDate, String certificate) {
        Achievement achievement = new Achievement();
        achievement.setStudent(student);
        achievement.setTitle(title);
        achievement.setDescription(description);
        achievement.setCategory(category);
        achievement.setAchievedDate(achievedDate);
        achievement.setCertificate(certificate);
        achievement.setActive(true);

        return achievementRepository.save(achievement);
    }

    public Achievement updateAchievement(Long id, String title, String description, String category, LocalDateTime achievedDate, String certificate) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        achievement.setTitle(title);
        achievement.setDescription(description);
        achievement.setCategory(category);
        achievement.setAchievedDate(achievedDate);
        achievement.setCertificate(certificate);

        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAchievementsByStudent(User student) {
        return achievementRepository.findByStudentAndActiveTrue(student);
    }

    public Achievement getAchievementById(Long id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
    }

    public void deleteAchievement(Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        achievement.setActive(false);
        achievementRepository.save(achievement);
    }
}
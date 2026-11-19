
package com.kiet.studentskillmanagement.service;

import com.kiet.studentskillmanagement.model.Student;
import com.kiet.studentskillmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Add student
    public Student addStudent(Student student) {

        if (repository.existsById(student.getStudentId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student ID already exists"
            );
        }

        validateScores(
                student.getProgrammingScore(),
                student.getMathematicsScore()
        );

        return repository.save(student);
    }

    // Validate scores
    private void validateScores(double p, double m) {

        if (p < 0 || p > 100 || m < 0 || m > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Scores must be between 0 and 100"
            );
        }
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // Search by ID
    public Student getStudentById(int id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );
    }

    // Update scores and skills
    @Transactional
    public Student updateStudent(
            int id,
            double programmingScore,
            double mathematicsScore,
            List<String> skills) {

        Student student = getStudentById(id);

        validateScores(programmingScore, mathematicsScore);

        student.setProgrammingScore(programmingScore);
        student.setMathematicsScore(mathematicsScore);

        student.setTechnicalSkills(
                skills == null ? new ArrayList<>() : skills
        );

        return repository.save(student);
    }

    // Delete student
    public void deleteStudent(int id) {

        Student student = getStudentById(id);
        repository.delete(student);
    }

    // Generate merit list
    public List<Student> getMeritList() {

        List<Student> students = repository.findAll();

        students.sort(
                Comparator.comparingDouble(
                        Student::getAverageScore
                ).reversed()
        );

        return students;
    }

    // Search by email
    public List<Student> searchByEmail(String email) {
        return repository
                .findByEmailAddressContainingIgnoreCase(email);
    }

    // Search by course
    public List<Student> searchByCourse(String course) {
        return repository
                .findByCourseNameContainingIgnoreCase(course);
    }

    // Find students with same technical skills
    public List<List<Student>> findStudentsWithSameSkills() {

        Map<String, List<Student>> skillGroups =
                new HashMap<>();

        for (Student student : repository.findAll()) {

            List<String> skills = student.getTechnicalSkills();

            if (skills == null || skills.isEmpty()) {
                continue;
            }

            String key = skills.stream()
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .sorted()
                    .collect(Collectors.joining(","));

            skillGroups.computeIfAbsent(
                    key, k -> new ArrayList<>()
            ).add(student);
        }

        return skillGroups.values().stream()
                .filter(group -> group.size() > 1)
                .collect(Collectors.toList());
    }

    // Students needing programming improvement
    public List<Student> getStudentsNeedingProgrammingImprovement() {

        return repository.findAll().stream()
                .filter(s -> s.getProgrammingScore() < 50)
                .filter(s -> s.getMathematicsScore() >= 75)
                .collect(Collectors.toList());
    }
}

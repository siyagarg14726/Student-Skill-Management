
package com.kiet.studentskillmanagement.controller;

import com.kiet.studentskillmanagement.model.Student;
import com.kiet.studentskillmanagement.dto.StudentUpdateRequest;
import com.kiet.studentskillmanagement.service.StudentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // 1. Add student
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @Valid @RequestBody Student student) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addStudent(student));
    }

    // 2. Retrieve all students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // 3. Search by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    // 4. Update scores and skills
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @Valid @RequestBody StudentUpdateRequest request) {

        return service.updateStudent(
                id,
                request.getProgrammingScore(),
                request.getMathematicsScore(),
                request.getTechnicalSkills()
        );
    }

    // 5. Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        service.deleteStudent(id);

        return ResponseEntity.ok(
                "Student deleted successfully"
        );
    }

    // 6. Display merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return service.getMeritList();
    }

    // Search by email
    @GetMapping("/search/email")
    public List<Student> searchByEmail(
            @RequestParam String email) {
        return service.searchByEmail(email);
    }

    // Search by course
    @GetMapping("/search/course")
    public List<Student> searchByCourse(
            @RequestParam String course) {
        return service.searchByCourse(course);
    }

    // Students with same technical skills
    @GetMapping("/same-skills")
    public List<List<Student>> sameSkills() {
        return service.findStudentsWithSameSkills();
    }

    // Programming improvement
    @GetMapping("/programming-improvement")
    public List<Student> programmingImprovement() {
        return service.getStudentsNeedingProgrammingImprovement();
    }
}

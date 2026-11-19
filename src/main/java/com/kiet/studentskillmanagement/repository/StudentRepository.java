package com.kiet.studentskillmanagement.repository;

import com.kiet.studentskillmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    List<Student> findByEmailAddressContainingIgnoreCase(String email);

    List<Student> findByCourseNameContainingIgnoreCase(String course);
}
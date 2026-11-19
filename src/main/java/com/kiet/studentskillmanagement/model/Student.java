
package com.kiet.studentskillmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private int studentId;

    @NotBlank
    private String studentName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String emailAddress;

    @NotBlank
    private String courseName;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double programmingScore;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double mathematicsScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "skill")
    private List<String> technicalSkills = new ArrayList<>();

    // Default constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(int studentId, String studentName,
                   String emailAddress, String courseName,
                   double programmingScore,
                   double mathematicsScore,
                   List<String> technicalSkills) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.emailAddress = emailAddress;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    // Getters and setters

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    // Calculate average
    public double getAverageScore() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    // Performance category
    public String getPerformanceCategory() {
        double avg = getAverageScore();

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    // Identify stronger subject
    public String getStrongerSubject() {

        if (programmingScore > mathematicsScore)
            return "Programming";
        else if (mathematicsScore > programmingScore)
            return "Mathematics";
        else
            return "Both";
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId
                + ", Name: " + studentName
                + ", Average: " + getAverageScore()
                + ", Category: " + getPerformanceCategory();
    }
}

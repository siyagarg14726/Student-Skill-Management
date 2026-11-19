package com.kiet.studentskillmanagement.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class StudentUpdateRequest {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double programmingScore;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double mathematicsScore;

    @NotNull
    private List<String> technicalSkills;

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
package com.school.miniinter.models.Teacher;

import java.util.List;

public class HomeTeacherInfo {

    private String full_name;
    private List<String> subjects;
    private Integer amountStudents;
    private Integer amountClass;
    private Integer amountObservations;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public Integer getAmountStudents() {
        return amountStudents;
    }

    public void setAmountStudents(Integer amountStudents) {
        this.amountStudents = amountStudents;
    }

    public Integer getAmountClass() {
        return amountClass;
    }

    public void setAmountClass(Integer amountClass) {
        this.amountClass = amountClass;
    }

    public Integer getAmountObservations() {
        return amountObservations;
    }

    public void setAmountObservations(Integer amountObservations) {
        this.amountObservations = amountObservations;
    }

    @Override
    public String toString() {
        return "HomeTeacherInfo{" +
                "full_name='" + full_name + '\'' +
                ", subjects=" + subjects +
                ", amountStudents=" + amountStudents +
                ", amountClass=" + amountClass +
                ", amountObservations=" + amountObservations +
                '}';
    }
}

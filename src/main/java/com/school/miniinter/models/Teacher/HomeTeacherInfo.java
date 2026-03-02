package com.school.miniinter.models.Teacher;

import java.util.List;

public class HomeTeacherInfo {

    private String full_name;
    private String first_name;
    private String subject;
    private Integer amountStudents;
    private Integer amountClass;
    private Integer amountObservations;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return "HomeTeacherInfo{" +
                "full_name='" + full_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", subject='" + subject + '\'' +
                ", amountStudents=" + amountStudents +
                ", amountClass=" + amountClass +
                ", amountObservations=" + amountObservations +
                '}';
    }
}

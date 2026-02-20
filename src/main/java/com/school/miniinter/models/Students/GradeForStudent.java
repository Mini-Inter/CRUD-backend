package com.school.miniinter.models.Students;

public class GradeForStudent {
    private String full_name;
    private Double n1;
    private Double n2;
    private Double average;

    public GradeForStudent(String full_name, Double n1, Double n2) {
        this.full_name = full_name;
        this.n1 = n1;
        this.n2 = n2;
        this.average =(n1+n2)/2;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Double getN1() {
        return n1;
    }

    public void setN1(Double n1) {
        this.n1 = n1;
    }

    public Double getN2() {
        return n2;
    }

    public void setN2(Double n2) {
        this.n2 = n2;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}

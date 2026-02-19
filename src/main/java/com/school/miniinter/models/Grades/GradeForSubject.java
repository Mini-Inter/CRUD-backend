package com.school.miniinter.models.Grades;

public class GradeForSubject {

    private String subject;
    private double n1;
    private double n2;
    private double average;
    private String situation;


    public GradeForSubject(String subject, double n1, double n2){
        this.subject = subject;
        this.n1 = n1;
        this.n2 = n2;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage() {
        this.average = (this.n1 + this.n2) / 2;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation() {
        if (this.average >= 7){
            this.situation = "Aprovado";
        }else{
            this.situation = "Reprovado";
        }
    }
}
